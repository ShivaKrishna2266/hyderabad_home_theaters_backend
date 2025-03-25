package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT cb FROM Category cb WHERE cb.categoryId = :categoryId")
    List<Category> findByCategoryId(@Param("categoryId") Long categoryId);
}
