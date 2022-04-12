package com.example.project_final_2.controller;

import com.example.project_final_2.dto.reponse.SuccessResponse;
import com.example.project_final_2.dto.request.InvoiceRequestDTO;
import com.example.project_final_2.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/show-invoice/{pageIndex}/{pageSize}")
    public ResponseEntity getAllInvoice(@PathVariable int pageIndex, @PathVariable int pageSize) throws Exception {
        return new ResponseEntity(new SuccessResponse(invoiceService.getAllInvoice(pageIndex, pageSize)), HttpStatus.OK);
    }

    @PostMapping("/add-invoice")
    public ResponseEntity createInvoice(@RequestBody InvoiceRequestDTO request) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        invoiceService.createInvoice(username, request);
        return new ResponseEntity(new SuccessResponse("Create invoice success"), HttpStatus.OK);
    }

    @PutMapping("/edit-invoice/{invoiceId}")
    public ResponseEntity editInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceRequestDTO request) throws Exception {
        invoiceService.editInvoice(invoiceId, request);
        return new ResponseEntity(new SuccessResponse("Edit invoice success"), HttpStatus.OK);
    }

    @DeleteMapping("/delete-invoice/{invoiceId}")
    public ResponseEntity deleteInvoice(@PathVariable Long invoiceId) throws Exception {
        invoiceService.deleteInvoice(invoiceId);
        return new ResponseEntity(new SuccessResponse("Delete invoice success"), HttpStatus.OK);
    }
}
