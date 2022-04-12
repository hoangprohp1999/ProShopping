package com.example.project_final_2.dto.reponse;

import com.example.project_final_2.entity.user.Role;
import com.example.project_final_2.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String fullName;

    private Date dOB;

    private char gender;

    private String identityCard;

    private String email;

    private String address;

    private String phoneNumber;

    private String password;

    private List<InvoiceResponseDTO> invoiceDTOs;

    private List<RatingResponseDTO> ratingDTOs;

    private Set<RoleDTOResponse> role;

//    private AccountStatus status;

    private static ModelMapper modelMapper = new ModelMapper();

    public static void autoSetUp() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
    }

    public static UserResponseDTO getDTOFrom(User user) {
        UserResponseDTO newDTO = modelMapper.map(user, UserResponseDTO.class);
        newDTO.setInvoiceDTOs(user.getInvoices().stream().map(invoice -> modelMapper
                .map(invoice,InvoiceResponseDTO.class)).collect(Collectors.toList()));

//        productResponseDTO.setProductCategoryDTOs(product.getProductCategories().stream()
//                .map(productCategory -> productCategory.getCategory().getCategoryName()).collect(Collectors.toList()));

        newDTO.setRatingDTOs((user.getRatings().stream()
                .map(rating -> modelMapper.map(rating,RatingResponseDTO.class)).collect(Collectors.toList())));
        /// cart DTO?
//        switch (mode) {
//            case ADMIN:
//                newDTO.roleDTOs = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
//                /// invoice + rating
//                break;
//            case OWNER:
//                newDTO.setPassword(null);
//                break;
//            default:
//                break;
//        }
        return newDTO;
    }

}
