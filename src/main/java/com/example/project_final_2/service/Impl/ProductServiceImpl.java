package com.example.project_final_2.service.Impl;

import com.example.project_final_2.dto.reponse.ProductResponseDTO;
import com.example.project_final_2.dto.request.ProductRequestDTO;
import com.example.project_final_2.entity.product.Image;
import com.example.project_final_2.entity.product.Product;
import com.example.project_final_2.entity.product.ProductCategory;
import com.example.project_final_2.repository.CategoryRepository;
import com.example.project_final_2.repository.ImageRepository;
import com.example.project_final_2.repository.ProductRepository;
import com.example.project_final_2.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    private final ImageRepository imageRepository;


    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<ProductResponseDTO> getAllProduct(int pageIndex,int pageSize) {
        return productRepository.findAll(PageRequest.of(pageIndex, pageSize)).stream().map(t -> ProductResponseDTO.getDTOFrom(t)).collect(Collectors.toList());
    }

    @Override
    public void editProduct(long id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(id).orElseThrow(()-> new IllegalStateException("Empty")) ;

        product.setProductName(productRequestDTO.getProductName());
        product.setAvailableQuantity(productRequestDTO.getAvailiableQuantity());
        product.setManufacturedFactory(productRequestDTO.getManufacturedFactory());
        product.setMaxQuantity(productRequestDTO.getMaxQuantity());
        product.setPrice(productRequestDTO.getPrice());
        product.setRatingScore(productRequestDTO.getRatingScore());
        product.setOrigin(productRequestDTO.getOrigin());

        List<Image> images = new ArrayList<>();
        productRequestDTO.getImages().forEach(url -> {
            Image imageOfProduct = new Image(url, product);
            images.add(imageOfProduct);
        });
        product.setImages(images);

        List<ProductCategory> productCategories = categoryRepository.findAllByIdIsIn(productRequestDTO.getCategoryIDs()).stream()
                .map(categoryName -> new ProductCategory(product,categoryName)).collect(Collectors.toList());
        product.setProductCategories(productCategories);

        productRepository.save(product);
    }

    @Override
    public void addProduct(ProductRequestDTO productRequestDTO) {
        Product product = modelMapper.map(productRequestDTO, Product.class);

        List<Image> images = new ArrayList<>();
        productRequestDTO.getImages().forEach(url -> {
            Image imageOfProduct = new Image(url, product);
            images.add(imageOfProduct);
        });
        product.setImages(images);
//        for (String image : productRequestDTO.getImages()) {
//            System.out.println(image);
//        }

        List<ProductCategory> productCategories = categoryRepository.findAllByIdIsIn(productRequestDTO.getCategoryIDs()).stream()
                .map(category -> new ProductCategory(product,category)).collect(Collectors.toList());
        product.setProductCategories(productCategories);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);
    }
}
