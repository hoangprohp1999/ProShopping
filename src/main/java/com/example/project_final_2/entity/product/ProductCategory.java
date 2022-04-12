package com.example.project_final_2.entity.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product_category")
@Getter
@Setter
@NoArgsConstructor
public class ProductCategory {

    @EmbeddedId
    private ProductCategoryId productCategoryId;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    public ProductCategory(Product product, Category category) {
        this.productCategoryId = new ProductCategoryId(product.getId(), category.getId());
        this.product = product;
        this.category = category;
    }

    public ProductCategory(Category category) {
        this.category = category;
    }
}
