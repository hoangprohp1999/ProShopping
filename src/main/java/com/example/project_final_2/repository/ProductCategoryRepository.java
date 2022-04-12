package com.example.project_final_2.repository;

import com.example.project_final_2.entity.product.ProductCategory;
import com.example.project_final_2.entity.product.ProductCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryId> {

}
