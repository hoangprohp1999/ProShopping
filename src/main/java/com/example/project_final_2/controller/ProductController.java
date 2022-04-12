package com.example.project_final_2.controller;

import com.example.project_final_2.dto.reponse.ProductResponseDTO;
import com.example.project_final_2.dto.reponse.SuccessResponse;
import com.example.project_final_2.dto.request.ProductRequestDTO;
import com.example.project_final_2.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/show-product/{pageIndex}/{pageSize}")
    public ResponseEntity getAllProduct(@PathVariable int pageIndex,@PathVariable int pageSize) {
        return new ResponseEntity(new SuccessResponse(productService.getAllProduct(pageIndex,pageSize)),HttpStatus.OK);
    }

    @PostMapping("/add-product")
    public ResponseEntity addNewProduct(@RequestBody ProductRequestDTO productRequestDTO){
        productService.addProduct(productRequestDTO);
        return new ResponseEntity(new SuccessResponse("Delete product success"), HttpStatus.OK);
    }

    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity deleteProduct(@PathVariable long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity(new SuccessResponse("Delete product success"), HttpStatus.OK);
    }

    @PutMapping("/edit-product/{productId}")
    public ResponseEntity editProduct(@PathVariable long productId, @RequestBody ProductRequestDTO productRequestDTO) {
        productService.editProduct(productId,productRequestDTO);
        return new ResponseEntity(new SuccessResponse("Delete product success"), HttpStatus.OK);
    }
}
