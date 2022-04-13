package com.example.project_final_2.entity.invoice;


import com.example.project_final_2.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "invoice_items")
public class InvoiceItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private InvoiceItemID invoiceItemID;

    @ManyToOne
    @MapsId("invoiceId")
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId",referencedColumnName = "productId")
    private Product product;

    @Column(name = "number_item")
    private int numberItem;

    @Column(name = "price")
    private long money;

    public InvoiceItems(Invoice invoice, Product item, int numberItem, long money) {
        this.invoiceItemID = new InvoiceItemID(invoice.getId(), item.getId());
        this.invoice = invoice;
        this.product = item;
        this.numberItem = numberItem;
        this.money = money;
    }
}
