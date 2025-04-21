package com.hyderabad_home_theaters.DTOs;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
