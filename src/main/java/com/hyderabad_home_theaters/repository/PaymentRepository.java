package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment , Long> {
    List<Payment> findByUserId(Long userId);
}
