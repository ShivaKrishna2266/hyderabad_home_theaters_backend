package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.QuestionsDTO;
import com.hyderabad_home_theaters.entity.Product;
import com.hyderabad_home_theaters.entity.Questions;
import com.hyderabad_home_theaters.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class QuestionsMapper {
    public QuestionsDTO convertToDTO(Questions questions) {
        QuestionsDTO questionsDTO = new QuestionsDTO();

        // Copy matching properties
        BeanUtils.copyProperties(questions, questionsDTO);

        // Handle the productId separately
        if (questions.getProduct() != null) {
            questionsDTO.setProductId(questions.getProduct().getProductId());
        }

        return questionsDTO;
    }

    public Questions convertToEntity(QuestionsDTO questionsDTO, ProductRepository productRepository) {
        Questions questions = new Questions();

        // Copy matching properties
        BeanUtils.copyProperties(questionsDTO, questions);

        // Handle setting the Product entity
        if (questionsDTO.getProductId() != null) {
            Product product = productRepository.findById(questionsDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product ID not found"));
            questions.setProduct(product);
        }

        return questions;
    }
}
