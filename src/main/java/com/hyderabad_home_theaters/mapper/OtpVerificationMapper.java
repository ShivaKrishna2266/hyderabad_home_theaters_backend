package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.OtpVerificationDTO;
import com.hyderabad_home_theaters.entity.OtpVerification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class OtpVerificationMapper {

    public static final ModelMapper modelMapper = new ModelMapper();

    public static OtpVerificationDTO convertToDTO(OtpVerification otpVerification) {
        OtpVerificationDTO dto = new OtpVerificationDTO();
        BeanUtils.copyProperties(otpVerification, dto);
        return dto;
    }

    public static OtpVerification convertToEntity(OtpVerificationDTO otpVerificationDTO) {
        OtpVerification otp = new OtpVerification();
        BeanUtils.copyProperties(otpVerificationDTO,otp);
        return otp;
    }
}
