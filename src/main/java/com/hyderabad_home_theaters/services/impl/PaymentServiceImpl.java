package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.entity.Payment;
import com.hyderabad_home_theaters.repository.PaymentRepository;
import com.hyderabad_home_theaters.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment makePayment(Payment payment) {
        payment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        payment.setPaymentStatus("SUCCESS");
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

}
