package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.ReviewDTO;

import java.util.List;

public interface ReviewServices {

    List<ReviewDTO> getAllReviews();

    ReviewDTO getReviewById(Long reviewId);

    ReviewDTO createReview(ReviewDTO reviewDTO);

    ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO);

    void deleteReviewById(Long reviewId);

    List<ReviewDTO> getReviewByProductId(Long productId);
}
