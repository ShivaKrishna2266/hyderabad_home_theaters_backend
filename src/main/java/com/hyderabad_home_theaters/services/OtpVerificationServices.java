package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.OtpVerificationDTO;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;

import java.util.List;

public interface OtpVerificationServices {


    List<OtpVerificationDTO> getAllOTPs() throws ApplicationBusinessException;
    OtpVerificationDTO createOtpVerification (OtpVerificationDTO otpVerificationDTO)throws ApplicationBusinessException;

    OtpVerificationDTO updateOtpVerifications (Long otpId, OtpVerificationDTO otpVerificationDTO)throws ApplicationBusinessException;

    OtpVerificationDTO updateOtpVerificationStatus (Long otpId, String otpStatus)throws ApplicationBusinessException;

    OtpVerificationDTO getOtpByMobileNumber(String mobileNumber) throws ApplicationBusinessException;

//    OtpVerificationDTO verifyOtpByMobileNumberAndOtpCode(String mobileNumber) throws ApplicationBusinessException;
    boolean validateOtp(String phoneNumber, String otpStatus);



}
