package com.example.project_final_2.service;

import com.example.project_final_2.dto.reponse.InvoiceResponseDTO;
import com.example.project_final_2.dto.request.InvoiceRequestDTO;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    List<InvoiceResponseDTO> getAllInvoice(int pageNumber, int limit);

    List<InvoiceResponseDTO> getAllInvoiceByUser(Long userID, int pageNumber, int limit);

    List<InvoiceResponseDTO> getAllInvoiceByProduct(Long productID, int pageNumber, int limit);

    void createInvoice(String username, InvoiceRequestDTO request) throws Exception;

    void editInvoice(long id, InvoiceRequestDTO request) throws Exception;

    void deleteInvoice(long invoiceID);
}
