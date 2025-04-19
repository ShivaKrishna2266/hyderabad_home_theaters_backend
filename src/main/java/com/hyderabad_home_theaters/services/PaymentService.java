package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.entity.Payment;

import java.util.List;

public interface PaymentService {

    Payment makePayment(Payment payment);
    List<Payment> getPaymentsByUserId(Long userId);
}
