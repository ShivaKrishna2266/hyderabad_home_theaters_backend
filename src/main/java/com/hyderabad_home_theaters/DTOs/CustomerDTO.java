package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Long customerId;
    private String firstName;
    private String username;
    private String surname;
    private String fullName;
    private String email;
    private String mobileNumber;

}
