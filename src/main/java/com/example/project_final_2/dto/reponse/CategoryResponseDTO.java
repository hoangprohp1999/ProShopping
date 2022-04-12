package com.example.project_final_2.dto.reponse;

import com.example.project_final_2.entity.product.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    private long id;
    private String categoryName;

}
