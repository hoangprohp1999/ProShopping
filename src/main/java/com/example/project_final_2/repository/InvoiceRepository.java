package com.example.project_final_2.repository;

import com.example.project_final_2.entity.invoice.Invoice;
import com.example.project_final_2.entity.invoice.InvoiceItems;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("select i from Invoice i where i.user.id IN(:userId)")
    List<Invoice> getInvoiceByUser(Long userId, Pageable pageable);

    boolean existsById(long id);

    @Query("select i from InvoiceItems i where i.product.id IN(:productId)")
    List<InvoiceItems> getInvoiceByProduct(Long productId, Pageable pageable);
}
