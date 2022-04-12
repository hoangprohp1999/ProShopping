package com.example.project_final_2.entity.product;

import com.example.project_final_2.entity.user.User;
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
@AllArgsConstructor
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

//    @ManyToOne
//    @JoinColumn(name = "userId", nullable = false)
//    private User user;

    public Image(String url, Product product) {
        this.url = url;
        this.product = product;
    }
}
