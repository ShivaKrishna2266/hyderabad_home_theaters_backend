package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.ReviewDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Product;
import com.hyderabad_home_theaters.entity.Review;
import com.hyderabad_home_theaters.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDto(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setReviewId(review.getReviewId());
        dto.setName(review.getName());
        dto.setEmail(review.getEmail());
        dto.setImage(review.getImage());
        dto.setRating(review.getRating());
        dto.setStatus(review.getStatus());
        dto.setReview(review.getReview());
        dto.setHeadline(review.getHeadline());

        if (review.getProduct() != null) {
            dto.setProductId(review.getProduct().getProductId());
        }

        return dto;
    }

    public Review toEntity(ReviewDTO dto, ProductRepository productRepository) {
        Review review = new Review();
        review.setReviewId(dto.getReviewId());
        review.setName(dto.getName());
        review.setEmail(dto.getEmail());
        review.setImage(dto.getImage());
        review.setRating(dto.getRating());
        review.setStatus(dto.getStatus());
        review.setReview(dto.getReview());
        review.setHeadline(dto.getHeadline());

        if (dto.getReviewId() != null) {
            Product  product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product ID not found"));
            review.setProduct(product);
        }
        return review;

    }
}