package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long > {

    @Query("SELECT p FROM Product p WHERE p.brand.brandId = :brandId")
    List<Product> findByBrandId(@Param("brandId") Long brandId);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

}
