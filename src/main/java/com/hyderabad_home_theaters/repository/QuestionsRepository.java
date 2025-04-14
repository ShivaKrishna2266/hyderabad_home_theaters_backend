package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Questions;
import com.hyderabad_home_theaters.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions , Long> {

    @Query("SELECT q FROM Questions q WHERE q.product.productId = :productId")
    List<Questions> findAllByProductId(@Param("productId") Long productId);
}
