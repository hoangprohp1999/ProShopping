package com.example.project_final_2.service.Impl;

import com.example.project_final_2.dto.enums.InvoiceStatus;
import com.example.project_final_2.dto.reponse.InvoiceResponseDTO;
import com.example.project_final_2.dto.request.InvoiceRequestDTO;
import com.example.project_final_2.entity.cart.CartItem;
import com.example.project_final_2.entity.invoice.Invoice;
import com.example.project_final_2.entity.invoice.InvoiceItems;
import com.example.project_final_2.entity.product.Product;
import com.example.project_final_2.entity.user.User;
import com.example.project_final_2.exception.BusinessException;
import com.example.project_final_2.exception.CanNotSaveToDatabaseException;
import com.example.project_final_2.exception.NotFoundEntityException;
import com.example.project_final_2.exception.NotFoundException;
import com.example.project_final_2.repository.CartRepository;
import com.example.project_final_2.repository.InvoiceRepository;
import com.example.project_final_2.repository.ProductRepository;
import com.example.project_final_2.repository.UserRepository;
import com.example.project_final_2.service.InvoiceService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final ModelMapper modelMapper;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;


    public InvoiceServiceImpl(ModelMapper modelMapper, InvoiceRepository invoiceRepository
            , ProductRepository productRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.modelMapper = modelMapper;
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }


    @Override
    public List<InvoiceResponseDTO> getAllInvoice(int pageIndex, int pageSize) {
        return invoiceRepository.findAll(PageRequest.of(pageIndex, pageSize)).stream()
                .map(invoice -> convertToDTO(invoice)).collect(Collectors.toList());
    }

    public InvoiceResponseDTO convertToDTO(Invoice invoice) {
        return InvoiceResponseDTO.getDTOFrom(invoice);
    }

    public List<InvoiceResponseDTO> convertAllToDTO(List<Invoice> invoices) {
        return invoices.stream().map(invoice -> convertToDTO(invoice)).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoiceByUser(Long userId, int pageIndex, int pageSize) {
        return invoiceRepository.getInvoiceByUser(userId, PageRequest.of(pageIndex, pageSize)).stream()
                .map(invoice -> convertToDTO(invoice)).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoiceByProduct(Long productId, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public void createInvoice(String username, InvoiceRequestDTO request) throws Exception {
        try {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new NotFoundEntityException("User not found"));
            if(user.getVerified() == true){
                Invoice invoice = getNewInvoiceFromDTO(username, request);
                invoiceRepository.save(invoice);
            }
            else{
                throw new NotFoundException("Not found");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CanNotSaveToDatabaseException(descriptionOf("Cannot save"));
        }
    }

    protected Invoice getNewInvoiceFromDTO(String username, InvoiceRequestDTO request) throws Exception {

        User userCreateInvoice = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundEntityException("User not found"));        //Get authenticated user

        List<CartItem> cart_items = cartRepository.findAllByUser_Id(userCreateInvoice.getId());

        if (cart_items.isEmpty()) throw new NotFoundEntityException("Create invoice fail");

        Invoice newInvoice = modelMapper.map(request,Invoice.class);

        List<InvoiceItems> invoice_items = cart_items.stream().map(cart_item ->
                        new InvoiceItems(newInvoice, cart_item.getProduct(), cart_item.getNumberItem(),
                                cart_item.getProduct().getPrice()))
                .collect(Collectors.toList());

        cart_items.forEach(cartItem -> {       //minus available quantity of product when create invoice
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new NotFoundEntityException("Product in cart not found"));

            int availableQuantityAfterCreateInvoice = product.getAvailableQuantity() - cartItem.getNumberItem();

            if (availableQuantityAfterCreateInvoice < 0) throw new BusinessException("Create invoice fail");

            product.setAvailableQuantity(availableQuantityAfterCreateInvoice);
            productRepository.save(product);
        });

        cartRepository.removeAllByUserId(userCreateInvoice.getId());          //remove product in cart when create invoice

        newInvoice.setInvoiceItems(invoice_items);
        newInvoice.setStatus(InvoiceStatus.ACCEPTED_ORDER);
        newInvoice.setTotalMoney(invoice_items.stream().mapToLong(items -> (items.getMoney() * items.getNumberItem())).sum());
        newInvoice.setUser(userCreateInvoice);

        return newInvoice;
    }

    @Override
    @Transactional
    public void editInvoice(long id, InvoiceRequestDTO request) throws Exception {
        try {
            Invoice invoice = getEditInvoiceFromDTO(id, request);
            invoiceRepository.save(invoice);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CanNotSaveToDatabaseException(descriptionOf("Cannot edit"));
        }
    }

    protected Invoice getEditInvoiceFromDTO(Long id, InvoiceRequestDTO request) throws Exception {
        Invoice invoiceToEdit = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Edit invoice fail"));

        //Cant edit when status of invoice is deliver or cancel
        if (invoiceToEdit.getStatus().toString().equals("DELIVER") ||
                invoiceToEdit.getStatus().toString().equals("CANCELED_ORDER")) {
            throw new BusinessException("Edit invoice fail");
        }

        InvoiceStatus newStatus = InvoiceStatus.valueOf(request.getStatus());

        if (newStatus.toString().equals("CANCELED_ORDER")) {             //add available quantity of product when invoice is canceled
            invoiceToEdit.getInvoiceItems().forEach(invoice_item -> {
                Product product = productRepository.findById(invoice_item.getProduct().getId())
                        .orElseThrow(() -> new NotFoundEntityException("Product in cart not found"));

                product.setAvailableQuantity(product.getAvailableQuantity() + invoice_item.getNumberItem());
                productRepository.save(product);
            });
        }

        invoiceToEdit.setStatus(newStatus);

        //Status change to deliver then minus max product
        if (invoiceToEdit.getStatus().toString().equals("DELIVER")) {
            invoiceToEdit.getInvoiceItems().forEach(invoice_item -> {
                Product product = productRepository.findById(invoice_item.getProduct().getId())
                        .orElseThrow(() -> new NotFoundEntityException("Product in cart not found"));

                product.setMaxQuantity(product.getMaxQuantity() - invoice_item.getNumberItem());
                productRepository.save(product);
            });
        }

        return invoiceToEdit;
    }


    protected String descriptionOf(String message) {
        return message;
    }


    @Override
    @Transactional
    public void deleteInvoice(long invoiceID) {
        if (!invoiceRepository.existsById(invoiceID)) throw new NotFoundEntityException("Delete fail");
        invoiceRepository.deleteById(invoiceID);
    }


}
