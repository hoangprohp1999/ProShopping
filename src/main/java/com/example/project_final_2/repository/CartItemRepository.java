package com.example.project_final_2.repository;

import com.example.project_final_2.entity.cart.CartItem;
import com.example.project_final_2.entity.product.UserProductKey;
import com.example.project_final_2.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UserProductKey> {

    @Transactional
    void deleteAllByUser(User user);

}
