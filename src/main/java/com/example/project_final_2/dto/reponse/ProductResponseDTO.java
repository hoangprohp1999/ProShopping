package com.example.project_final_2.dto.reponse;

import com.example.project_final_2.dto.enums.ViewMode;
import com.example.project_final_2.entity.product.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private long id;
    private int availableQuantity;
    private String manufacturedFactory;
    private int maxQuantity;
    private String productName;
    private int price;
    private float ratingScore;
    private String origin;
    private Category category;
    private List<ImageResponseDTO> imageDTOs;
    private List<CategoryResponseDTO> productCategoryDTOs;
    private static ModelMapper modelMapper = new ModelMapper();

    public static void setModelMapper(){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
    }

    public static ProductResponseDTO getDTOFrom(Product product){

        ProductResponseDTO productResponseDTO = modelMapper.map(product,ProductResponseDTO.class);

        productResponseDTO.setImageDTOs(product.getImages().stream().map(image -> modelMapper
                .map(image,ImageResponseDTO.class)).collect(Collectors.toList()));

//        productResponseDTO.setProductCategoryDTOs(product.getProductCategories().stream()
//                .map(productCategory -> productCategory.getCategory().getCategoryName()).collect(Collectors.toList()));

        productResponseDTO.setProductCategoryDTOs((product.getProductCategories().stream()
                .map(productCategory -> modelMapper.map(productCategory.getCategory(),CategoryResponseDTO.class)).collect(Collectors.toList())));

//        switch (mode){
//            case ADMIN:
//                break;
//            case CUSTOMER:
//                productResponseDTO.setMaxQuantity(-1);
//                break;
//            default:
//                break;
//        }

        return productResponseDTO;
    }

}
