package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.BannerDTO;
import com.hyderabad_home_theaters.entity.Banner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class BannerMapper {

    public static final ModelMapper modelMapper = new ModelMapper();

    public static BannerDTO  convertToDTO (Banner banner){
        BannerDTO dto = new BannerDTO();
        BeanUtils.copyProperties(banner, dto);
        return  dto;
    }

    public static Banner convertToEntity(BannerDTO bannerDTO){
        Banner banner = new Banner();
        BeanUtils.copyProperties(bannerDTO, banner);
        return banner;
    }
}
