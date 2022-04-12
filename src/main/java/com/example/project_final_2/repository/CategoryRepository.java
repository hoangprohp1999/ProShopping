package com.example.project_final_2.repository;

import com.example.project_final_2.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAllByIdIsIn(Collection<Long> ids);
}
