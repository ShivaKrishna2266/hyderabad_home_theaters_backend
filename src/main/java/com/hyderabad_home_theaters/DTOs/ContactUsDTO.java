package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUsDTO {

    private Long id;
    private String name;
    private String emailId;
    private String phoneNo;
    private Integer countryCode;
    private String subject;
    private String message;
    private String comment;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String leadStatus;
}
