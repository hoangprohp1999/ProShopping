package com.example.project_final_2.service;

import com.example.project_final_2.dto.reponse.CartResponseDTO;
import com.example.project_final_2.dto.request.CartItemRequestDTO;

import java.util.List;

public interface CartService {
    void addItemToCart(String username, CartItemRequestDTO cartItemRequestDTO);
    void editItemToCart(String username, CartItemRequestDTO cartItemRequestDTO);
    void deleteCartItem(String username,long productId);
    CartResponseDTO showAllCart(long userId);
    void resetCart(String username);
}
