package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.HeaderDTO;
import com.hyderabad_home_theaters.entity.Header;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class HeaderMapper {

    public static final ModelMapper modelMapper = new ModelMapper();

    public static HeaderDTO convertToDTO(Header header) {
        HeaderDTO dto = new HeaderDTO();
        BeanUtils.copyProperties(header, dto);
        return dto;
    }

    public static Header convertToEntity(HeaderDTO headerDTO) {
        Header otp = new Header();
        BeanUtils.copyProperties(headerDTO,otp);
        return otp;
    }
}
