package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.OtpVerificationDTO;
import com.hyderabad_home_theaters.entity.OtpVerification;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;
import com.hyderabad_home_theaters.mapper.OtpVerificationMapper;
import com.hyderabad_home_theaters.repository.OtpVerificationRepository;
import com.hyderabad_home_theaters.services.OtpVerificationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OtpVerificationServiceImpl implements OtpVerificationServices {

    @Autowired
    private OtpVerificationRepository otpRepo;

    @Override
    public List<OtpVerificationDTO> getAllOTPs() throws ApplicationBusinessException {
        List<OtpVerification> otpEntities = otpRepo.findAll();
        return otpEntities.stream()
                .map(OtpVerificationMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OtpVerificationDTO createOtpVerification(OtpVerificationDTO otpVerificationDTO) throws ApplicationBusinessException {
        try {
            OtpVerification entity = OtpVerificationMapper.convertToEntity(otpVerificationDTO);
            entity.setOtpCode(otpVerificationDTO.getOtpCode());  // Ensure OTP is set
            entity.setCreatedBy("System");
            entity.setUpdatedBy("System");
            entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            OtpVerification saved = otpRepo.save(entity);
            return OtpVerificationMapper.convertToDTO(saved);
        } catch (Exception e) {
            throw new ApplicationBusinessException("Error while adding OTP");
        }
    }

    @Override
    public OtpVerificationDTO updateOtpVerifications(Long otpId, OtpVerificationDTO otpVerificationDTO) throws ApplicationBusinessException {
        try {
            OtpVerification entity = otpRepo.findById(otpId)
                    .orElseThrow(() -> new ApplicationBusinessException("OTP not found with ID: " + otpId));
            entity.setOtpStatus(otpVerificationDTO.getOtpStatus());
            entity.setMobileNumber(otpVerificationDTO.getMobileNumber());
            entity.setEmail(otpVerificationDTO.getEmail());
            entity.setOtp(otpVerificationDTO.getOtp());
            entity.setUpdatedBy("System");
            entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            otpRepo.save(entity);
            return OtpVerificationMapper.convertToDTO(entity);
        } catch (Exception e) {
            throw new ApplicationBusinessException("Error while updating OTP");
        }
    }

    @Override
    public OtpVerificationDTO updateOtpVerificationStatus(Long otpId, String otpStatus) throws ApplicationBusinessException {
        Optional<OtpVerification> otpOpt = otpRepo.findById(otpId);
        if (otpOpt.isPresent()) {
            OtpVerification otp = otpOpt.get();
            otp.setOtpStatus(otpStatus);
            otp.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            return OtpVerificationMapper.convertToDTO(otpRepo.save(otp));
        }
        throw new ApplicationBusinessException("OTP not found with ID: " + otpId);
    }

    @Override
    public OtpVerificationDTO getOtpByMobileNumber(String mobileNumber) throws ApplicationBusinessException {
        OtpVerification otpOpt = otpRepo.findByMobileNumber(mobileNumber);
        if (otpOpt == null) {
            throw new ApplicationBusinessException("OTP not found for mobile number: " + mobileNumber);
        }
        otpOpt.setOtpStatus("Verified");
        otpRepo.save(otpOpt);
        return OtpVerificationMapper.convertToDTO(otpOpt);
    }

    @Override
    public OtpVerificationDTO verifyOtpByMobileNumberAndOtpCode(String mobileNumber, String otpCode) throws ApplicationBusinessException {
        Optional<OtpVerification> otpOptional = otpRepo.findByMobileNumberAndOtpCode(mobileNumber, otpCode);
        if (!otpOptional.isPresent()) {
            throw new ApplicationBusinessException("OTP not found for mobile number: " + mobileNumber + " and OTP code: " + otpCode);
        }
        OtpVerification otpCode1 = otpOptional.get();
        otpCode1.setOtpStatus("Verified");
        otpRepo.save(otpCode1);
        return OtpVerificationMapper.convertToDTO(otpCode1);
    }


    @Override
    public boolean validateOtp(String mobileNumber, String otpStatus) {
        return otpRepo.existsByMobileNumberAndOtpStatus(mobileNumber, otpStatus);
    }
}
