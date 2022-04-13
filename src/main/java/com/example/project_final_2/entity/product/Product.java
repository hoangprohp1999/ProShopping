package com.example.project_final_2.entity.product;

import com.example.project_final_2.entity.cart.CartItem;
import com.example.project_final_2.entity.invoice.InvoiceItems;
import com.example.project_final_2.entity.user.Rating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private long id;

    @Column(name = "availableQuantity")
    private int availableQuantity;

    @Column(name = "manufacturedFactory")
    private String manufacturedFactory;

    @Column(name = "maxQuantity")
    private long maxQuantity;

    @Column(name = "productName")
    private String productName;

    @Column(name = "price")
    private int price;

    @Column(name = "ratingScore")
    private float ratingScore;

    @Column(name = "origin")
    private String origin;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ProductCategory> productCategories;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Image> images;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<CartItem> cartItems;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<InvoiceItems> invoice_items;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Rating> ratings;

}
