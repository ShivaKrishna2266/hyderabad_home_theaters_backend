package com.hyderabad_home_theaters.DTOs.payment;

import lombok.Data;

@Data
public class OrderResponse {

    private String applicationFee;
    private String razorpayOrderId;
    private String secretKey;
}
