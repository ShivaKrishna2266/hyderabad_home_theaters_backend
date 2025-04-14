package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsDTO {

    private Long questionId;
    private String userName;
    private String userEmail;
    private String question;
    private String answer;
    private String image;
    private String status;
    private Long productId;
}
