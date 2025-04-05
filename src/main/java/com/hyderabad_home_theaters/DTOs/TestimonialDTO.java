package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialDTO {
    private Long testimonialId;
    private String name;
    private String role;
    private String message;
    private String image;
    private String designation;
    private String description;
    private String star;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp updatedAt;
    private String updatedBy;
    private String status;
}
