package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.OrderDTO;
import com.hyderabad_home_theaters.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class OrderMapper {

    public static final ModelMapper modelMapper = new ModelMapper();

    public static OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(order, dto);
        return dto;
    }

    public static Order convertToEntity(OrderDTO orderDTO) {
        Order otp = new Order();
        BeanUtils.copyProperties(orderDTO,otp);
        return otp;
    }

}
