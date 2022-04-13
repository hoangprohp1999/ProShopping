package com.example.project_final_2.service.Impl;

import com.example.project_final_2.dto.reponse.CartResponseDTO;
import com.example.project_final_2.dto.reponse.ItemResponseDTO;
import com.example.project_final_2.dto.reponse.ProductResponseDTO;
import com.example.project_final_2.dto.request.CartItemRequestDTO;
import com.example.project_final_2.entity.cart.CartItem;
import com.example.project_final_2.entity.product.Product;
import com.example.project_final_2.entity.product.UserProductKey;
import com.example.project_final_2.entity.user.User;
import com.example.project_final_2.exception.BusinessException;
import com.example.project_final_2.repository.CartItemRepository;
import com.example.project_final_2.repository.ProductRepository;
import com.example.project_final_2.repository.UserRepository;
import com.example.project_final_2.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public CartServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addItemToCart(String username, CartItemRequestDTO cartItemRequestDTO) {

        User user = userRepository.findByEmail(username).orElseThrow(()->new IllegalStateException("Empty"));

        Product product = productRepository.findById(cartItemRequestDTO.getProductId()).orElseThrow(()-> new IllegalStateException("Not Found"));

        int availableQuantityAfterCreateInvoice = product.getAvailableQuantity() - cartItemRequestDTO.getNumberItem();

        if (availableQuantityAfterCreateInvoice < 0) throw new BusinessException("Create cart fail");

//        if(availableQuantity< numberOfItem)
//            throw new DataFoundExcecption("Not enough"

        cartItemRepository.save(new CartItem(user,product,cartItemRequestDTO.getNumberItem()));
    }

    @Override
    public void editItemToCart(String username, CartItemRequestDTO cartItemRequestDTO) {
        User user = userRepository.findByEmail(username).orElseThrow(()->new IllegalStateException("Empty"));

        Product product = productRepository.findById(cartItemRequestDTO.getProductId()).orElseThrow(()-> new IllegalStateException("Not Found"));

        long availableQuantity = product.getAvailableQuantity();

//        if(availableQuantity< numberOfItem)
//            throw new DataFoundExcecption("Not enough");

        cartItemRepository.save(new CartItem(user ,product,cartItemRequestDTO.getNumberItem()));
    }

    @Override
    public void deleteCartItem(String username, long productId) {
        User user = userRepository.findByEmail(username).orElseThrow(()->new IllegalStateException("Empty"));

        Product product = productRepository.findById((productId)).orElseThrow(() -> new IllegalStateException("Not Found"));
        UserProductKey key = new UserProductKey(user.getId(),productId);

        try {
            cartItemRepository.deleteById(key);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new IllegalStateException("Not Found Item");
        }
    }

    @Override
    public CartResponseDTO showAllCart(long userId) {

        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("Empty"));

        AtomicLong totalAll = new AtomicLong();

        List<ItemResponseDTO> items = user.getCartItems().stream().map(cartItem -> {
            ProductResponseDTO dto = ProductResponseDTO.getDTOFrom(cartItem.getProduct());
            long total = cartItem.getNumberItem()*dto.getPrice();
            totalAll.addAndGet(total);
            return new ItemResponseDTO(dto, cartItem.getNumberItem(), total);
        }).collect(Collectors.toList());

        return new CartResponseDTO(totalAll.get(),items);
    }

    @Override
    @Transactional
    public void resetCart(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(()->new IllegalStateException("Empty"));
        cartItemRepository.deleteAllByUser(user);
    }
}
