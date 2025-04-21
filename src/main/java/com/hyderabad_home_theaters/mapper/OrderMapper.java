package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.OrderDTO;
import com.hyderabad_home_theaters.entity.Orders;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class OrderMapper {

    public static final ModelMapper modelMapper = new ModelMapper();

    public static OrderDTO convertToDTO(Orders order) {
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(order, dto);
        return dto;
    }

    public static Orders convertToEntity(OrderDTO orderDTO) {
        Orders otp = new Orders();
        BeanUtils.copyProperties(orderDTO,otp);
        return otp;
    }

}
