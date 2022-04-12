package com.example.project_final_2.repository;

import com.example.project_final_2.entity.product.UserProductKey;
import com.example.project_final_2.entity.user.Rating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, UserProductKey> {
    List<Rating> findAllByProduct_Id(Long productId, Pageable pageable);

    Boolean existsByUserProductKey(UserProductKey userProductKey);

    Optional<Rating> findByUserProductKey(UserProductKey userProductKey);

    void deleteByUserProductKey(UserProductKey userProductKey);
}
