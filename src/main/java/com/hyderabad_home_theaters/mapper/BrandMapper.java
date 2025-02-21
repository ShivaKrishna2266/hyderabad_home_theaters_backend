package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.entity.Brand;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class BrandMapper {
    public static final ModelMapper madelMapper =new ModelMapper();

    public static BrandDTO convertToDTO(Brand brand){
        BrandDTO brandDTO = new BrandDTO();
        BeanUtils.copyProperties(brand, brandDTO);
        return  brandDTO;
    }

    public static Brand convertToEntity(BrandDTO brandDTO){
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandDTO, brand);
        return brand;
    }
}
