package com.example.project_final_2.dto.reponse;

import com.example.project_final_2.dto.enums.InvoiceStatus;
import com.example.project_final_2.entity.invoice.Invoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponseDTO {
    private static ModelMapper modelMapper = new ModelMapper();
    private Long id;
    private Long userID;
    private List invoiceItemID;
    private long totalMoney;
    private InvoiceStatus status;

    public static void setModelMapper(){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
    }

    public static InvoiceResponseDTO getDTOFrom(Invoice invoice){
        InvoiceResponseDTO dto = modelMapper.map(invoice, InvoiceResponseDTO.class);

        dto.setUserID(invoice.getUser().getId());

        dto.setInvoiceItemID(invoice.getInvoiceItems().stream()
                .map(invoice_item -> invoice_item.getNumberItem())
                .collect(Collectors.toList()));

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
