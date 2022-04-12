package com.example.project_final_2.entity.invoice;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class InvoiceItemID implements Serializable {

    @Column(name = "invoice_id")
    private long invoiceId;

    @Column(name = "item_id")
    private Long productId;

}
