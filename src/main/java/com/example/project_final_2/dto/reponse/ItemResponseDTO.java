package com.example.project_final_2.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDTO {
    private ProductResponseDTO productResponseDTO;
    private long numberOfProduct;
    private long total;

}
