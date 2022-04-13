package com.example.project_final_2.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartItemRequestDTO {

    @NotNull
    private Long productId;

    @Positive
    private int numberItem;
}
