package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.CustomerDTO;
import com.hyderabad_home_theaters.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class CustomerMapper {


    public static final ModelMapper modelMapper = new ModelMapper();

    public static CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer, dto);
        return dto;
    }

    public static Customer convertToEntity(CustomerDTO customerDTO) {
        Customer otp = new Customer();
        BeanUtils.copyProperties(customerDTO,otp);
        return otp;
    }
}
