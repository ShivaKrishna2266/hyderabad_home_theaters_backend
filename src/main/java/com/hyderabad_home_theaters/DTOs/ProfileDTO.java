package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    private String fullName;
    private String firstName;
    private String username;
    private String surname;
    private String email;
    private String mobileNumber;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String area;
    private String city;
    private String postCode;
    private String region;
    private String state;
    private String country;
}
