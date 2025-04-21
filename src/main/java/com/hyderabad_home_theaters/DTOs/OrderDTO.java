package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private  Long orderId;
    private Long userId;
    private String username;
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
    private String email;
    private String mobileNumber;
    private String amount;
    private String profile;
    private String orderStatus;
}
