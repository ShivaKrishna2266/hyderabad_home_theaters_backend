package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.QuestionsDTO;

import java.util.List;

public interface QuestionsServices {

    List<QuestionsDTO> getAllQuestions();

    QuestionsDTO getQuestionById(Long questionId);

    QuestionsDTO createQuestion(QuestionsDTO questionsDTO);

    QuestionsDTO updateQuestion(Long questionId, QuestionsDTO questionsDTO);

    void deleteQuestionById(Long questionId);

    List<QuestionsDTO> getQuestionByProductId(Long productId);
}
