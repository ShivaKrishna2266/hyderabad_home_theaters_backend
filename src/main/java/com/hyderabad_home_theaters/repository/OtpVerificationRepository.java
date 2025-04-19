package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    OtpVerification findByMobileNumber(String mobileNumber);
    boolean existsByMobileNumberAndOtpStatus(String mobileNumber, String otpStatus);
}
