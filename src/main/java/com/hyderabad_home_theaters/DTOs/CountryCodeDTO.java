package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryCodeDTO {

    private Long id;
    private String countryName;
    private String countryCode;
    private String countryFlag;
    private int status;
}

