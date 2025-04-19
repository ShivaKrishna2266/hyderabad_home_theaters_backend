package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;
    private Long userId;
    private String productName;
    private Integer quantity;
    private Double totalPrice;
    private String status; // PENDING, PAID, SHIPPED, etc.
    private String paymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
    private String customerName;
    private String email;
    private String mobileNumber;
    private String profile;
    private String createdBy;
    private Timestamp createdDate;
    private String updatedBy;
    private Timestamp updatedDate;
}
