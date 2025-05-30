package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;
    private String otp;
    private String otpCode;
    private String createdBy;
    private Timestamp createDate;
    private String updatedBy;
    private Timestamp updatedDate;

    private String resetToken;
    private Timestamp tokenExpiry;
}
