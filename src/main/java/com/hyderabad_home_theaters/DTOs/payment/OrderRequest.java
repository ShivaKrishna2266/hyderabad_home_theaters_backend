package com.hyderabad_home_theaters.DTOs.payment;

import com.hyderabad_home_theaters.DTOs.ProfileDTO;
import lombok.Data;

@Data
public class OrderRequest {

    private Long userId;
    private String customerName;
    private String email;
    private String mobileNumber;
    private String amount;
    private ProfileDTO profile;
}
