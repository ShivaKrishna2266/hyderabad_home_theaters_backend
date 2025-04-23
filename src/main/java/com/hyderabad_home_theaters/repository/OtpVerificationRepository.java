package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    OtpVerification findByMobileNumber(String mobileNumber);
    boolean existsByMobileNumberAndOtpStatus(String mobileNumber, String otpStatus);

    @Query("SELECT o FROM OtpVerification o WHERE o.mobileNumber = :mobileNumber AND o.otp = :otp")
    Optional<OtpVerification> findByMobileNumberAndOtpCode(@Param("mobileNumber") String mobileNumber, @Param("otp") String otp);

    Optional<OtpVerification> findTopByMobileNumberOrderByCreatedAtDesc(String mobileNumber);

}
