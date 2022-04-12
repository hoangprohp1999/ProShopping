package com.example.project_final_2.repository;

import com.example.project_final_2.entity.cart.CartItem;
import com.example.project_final_2.entity.product.UserProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, UserProductKey> {
    List<CartItem> findAllByUser_Id(Long id);

    @Transactional
    void removeAllByUserId(Long userId);
}
