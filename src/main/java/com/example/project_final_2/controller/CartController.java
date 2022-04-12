package com.example.project_final_2.controller;

import com.example.project_final_2.dto.reponse.CartResponseDTO;
import com.example.project_final_2.dto.reponse.SuccessResponse;
import com.example.project_final_2.dto.request.CartItemRequestDTO;
import com.example.project_final_2.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;


    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping("/add-cart")
    public ResponseEntity addCartItem(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        cartService.addItemToCart(username, cartItemRequestDTO);
        return new ResponseEntity(new SuccessResponse("Create cart success"), HttpStatus.OK);
    }

    @PutMapping("/edit-cart")
    public ResponseEntity editCartItem(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        cartService.editItemToCart(username, cartItemRequestDTO);
        return new ResponseEntity(new SuccessResponse("Edit cart success"), HttpStatus.OK);
    }

    @DeleteMapping("/delete-cart/{productId}")
    public ResponseEntity deleteCart(@PathVariable long productId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        cartService.deleteCartItem(username,productId);
        return new ResponseEntity(new SuccessResponse("Delete cart success"), HttpStatus.OK);
    }

    @GetMapping("/show-cart/{userId}")
    public ResponseEntity showAllCart(@PathVariable long userId){
        return new ResponseEntity(new SuccessResponse(cartService.showAllCart(userId)),HttpStatus.OK);
    }

    @DeleteMapping("/reset-cart")
    public ResponseEntity resetCart(){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        cartService.resetCart(username);
        return new ResponseEntity(new SuccessResponse("Reset cart success"), HttpStatus.OK);
    }
}
