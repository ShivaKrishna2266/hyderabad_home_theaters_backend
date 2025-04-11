package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.ProductDTO;
import com.hyderabad_home_theaters.DTOs.ReviewDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Product;
import com.hyderabad_home_theaters.entity.Review;
import com.hyderabad_home_theaters.mapper.ProductMapper;
import com.hyderabad_home_theaters.mapper.ReviewMapper;
import com.hyderabad_home_theaters.repository.ProductRepository;
import com.hyderabad_home_theaters.repository.ReviewRepository;
import com.hyderabad_home_theaters.services.ReviewServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServicesImpl implements ReviewServices {


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toDto)
                .orElse(null);
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {

        Review review = reviewMapper.toEntity(reviewDTO, productRepository);
        review.setCreatedBy("System");
        review.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        review.setUpdatedBy("System");
        review.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + reviewDTO.getProductId()));
        review.setProduct(product);

        Review review1 = reviewRepository.save(review);
        ReviewDTO reviewDTO1 = reviewMapper.toDto(review1);

        return reviewDTO1;
    }

    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.setName(reviewDTO.getName());
            review.setEmail(reviewDTO.getEmail());
            review.setImage(reviewDTO.getImage());
            review.setRating(reviewDTO.getRating());
            review.setStatus(reviewDTO.getStatus());
            review.setUpdatedBy("System");
            review.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

            Review savedReview = reviewRepository.save(review);
            return reviewMapper.toDto(savedReview);
        }
        return null;
    }

    @Override
    public void deleteReviewById(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
        } else {
            throw new RuntimeException("Review not found with ID: " + id);
        }
    }

    @Override
    public List<ReviewDTO> getReviewByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }
}