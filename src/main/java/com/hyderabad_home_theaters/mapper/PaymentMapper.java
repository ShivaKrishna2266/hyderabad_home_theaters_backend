package com.hyderabad_home_theaters.mapper;


import com.hyderabad_home_theaters.DTOs.PaymentDTO;
import com.hyderabad_home_theaters.entity.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class PaymentMapper {

    public static final ModelMapper modelMapper = new ModelMapper();

    public static PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        BeanUtils.copyProperties(payment, dto);
        return dto;
    }

    public static Payment convertToEntity(PaymentDTO paymentDTO) {
        Payment otp = new Payment();
        BeanUtils.copyProperties(paymentDTO,otp);
        return otp;
    }
}
