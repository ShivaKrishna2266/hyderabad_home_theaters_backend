package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {

    @Query("SELECT p FROM SubCategory p WHERE p.category.categoryId = :categoryId")
    List<SubCategory> findByCategoryId(@Param("categoryId") Long categoryId);
}
