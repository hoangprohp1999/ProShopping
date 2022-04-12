package com.example.project_final_2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private long productId;
    private String productName;
    private int price;
    private int maxQuantity;
    private int availiableQuantity;
    private String manufacturedFactory;
    private String origin;
    private float ratingScore;
    private List<Long> categoryIDs;
    private List<String> images;
}
