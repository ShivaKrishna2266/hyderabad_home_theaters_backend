package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.ReviewDTO;

import java.util.List;

public interface ReviewServices {

    List<ReviewDTO> getAllReviews();

    ReviewDTO getReviewById(Long id);

    ReviewDTO createReview(ReviewDTO reviewDTO);

    ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);

    void deleteReviewById(Long id);

    List<ReviewDTO> getReviewByProductId(Long productId);
}
