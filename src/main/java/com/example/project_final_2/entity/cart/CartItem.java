package com.example.project_final_2.entity.cart;

import com.example.project_final_2.entity.product.Product;
import com.example.project_final_2.entity.product.UserProductKey;
import com.example.project_final_2.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem {

    @EmbeddedId
    private UserProductKey userProductKey;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "numberItem")
    private long numberItem;

    public CartItem(User user, Product product, long numberItem) {
        this.userProductKey = new UserProductKey(user.getId(),product.getId());
        this.user = user;
        this.product = product;
        this.numberItem = numberItem;
    }
}
