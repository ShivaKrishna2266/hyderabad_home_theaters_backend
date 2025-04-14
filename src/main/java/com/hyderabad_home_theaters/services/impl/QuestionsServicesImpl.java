package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.QuestionsDTO;
import com.hyderabad_home_theaters.constants.AppConstants;
import com.hyderabad_home_theaters.entity.Product;
import com.hyderabad_home_theaters.entity.Questions;
import com.hyderabad_home_theaters.entity.Review;
import com.hyderabad_home_theaters.mapper.QuestionsMapper;
import com.hyderabad_home_theaters.repository.ProductRepository;
import com.hyderabad_home_theaters.repository.QuestionsRepository;
import com.hyderabad_home_theaters.services.QuestionsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionsServicesImpl implements QuestionsServices {

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private  QuestionsMapper questionsMapper;

    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<QuestionsDTO> getAllQuestions() {
        List<Questions> questions = questionsRepository.findAll();
        return questions.stream()
                .map(questionsMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionsDTO getQuestionById(Long questionId) {
        Optional<Questions> optionalQuestions =questionsRepository.findById(questionId);

       if (optionalQuestions.isPresent()){
           Questions questions = optionalQuestions.get();
           return questionsMapper.convertToDTO(questions); // Correct conversion
       }
       return null;
    }

    @Override
    public QuestionsDTO createQuestion(QuestionsDTO questionsDTO) {
        // Convert DTO to Entity and set the product
        Questions questions = questionsMapper.convertToEntity(questionsDTO, productRepository);

        // Set audit fields and default status
        questions.setStatus(AppConstants.ACTIVE);
        questions.setCreatedBy("System");
        questions.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        questions.setUpdatedBy("System");
        questions.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

        // Save to DB
        Questions savedQuestion = questionsRepository.save(questions);

        // Convert saved entity back to DTO
        return questionsMapper.convertToDTO(savedQuestion);
    }


    @Override
    public QuestionsDTO updateQuestion(Long questionId, QuestionsDTO questionsDTO) {
        Questions existingQuestion = questionsRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));


        existingQuestion.setUserName(questionsDTO.getUserName());
        existingQuestion.setUserEmail(questionsDTO.getUserEmail());
        existingQuestion.setQuestion(questionsDTO.getQuestion());
        existingQuestion.setAnswer(questionsDTO.getAnswer());
        existingQuestion.setImage(questionsDTO.getImage());
        existingQuestion.setStatus(AppConstants.ACTIVE);
        existingQuestion.setUpdatedBy("System");
        existingQuestion.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

        if (questionsDTO.getProductId() != null) {
            Product product = productRepository.findById(questionsDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + questionsDTO.getProductId()));
            existingQuestion.setProduct(product);
        }

        Questions updatedQuestion = questionsRepository.save(existingQuestion);
        return questionsMapper.convertToDTO(updatedQuestion);
    }


    @Override
    public void deleteQuestionById(Long questionId) {
        if(questionsRepository.existsById(questionId)){
        questionsRepository.deleteById(questionId);
        } else {
        throw new RuntimeException("Question not found with ID: " + questionId);
        }
    }

    @Override
    public List<QuestionsDTO> getQuestionByProductId(Long productId) {
        List<Questions> questions = questionsRepository.findAllByProductId(productId);
        return questions.stream()
                .map(questionsMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
