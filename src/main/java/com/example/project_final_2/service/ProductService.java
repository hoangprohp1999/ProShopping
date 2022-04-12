package com.example.project_final_2.service;

import com.example.project_final_2.dto.reponse.ProductResponseDTO;
import com.example.project_final_2.dto.request.ProductRequestDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProduct(int pageIndex, int pageSize);
    void addProduct(ProductRequestDTO productRequestDTO);
    void deleteProduct(long productId);
    void editProduct(long id, ProductRequestDTO productRequestDTO);
}
