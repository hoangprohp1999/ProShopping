package com.example.project_final_2.dto.reponse;

import com.example.project_final_2.entity.user.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponseDTO {
    private static ModelMapper modelMapper = new ModelMapper();
    private long userID;
    private long productID;
    private float score;
    private String comment;
    private LocalDateTime creatAt;

    public static void setModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
    }

    public static RatingResponseDTO getDTOFrom(Rating rating) {
        RatingResponseDTO dto = modelMapper.map(rating, RatingResponseDTO.class);
        dto.setProductID(rating.getProduct().getId());
        dto.setUserID(rating.getUser().getId());

//        switch (mode){
//            case ADMIN:
//                break;
//            case CUSTOMER:
//                break;
//            default:
//                break;
//        }

        return dto;
    }
}
