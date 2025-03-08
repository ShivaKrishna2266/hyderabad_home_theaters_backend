package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.entity.Brand;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class BrandMapper {
    public static final ModelMapper madelMapper =new ModelMapper();

    public static BrandDTO convertToDTO(Brand brand){
        BrandDTO brandDTO = new BrandDTO();

        brandDTO.setBrandId(brand.getBrandId());
        brandDTO.setBrandName(brand.getBrandName());
        brandDTO.setBrandDescription(brand.getBrandDescription());
        brandDTO.setTagLine(brand.getTagLine());
        brandDTO.setImageName(brand.getImageName());
        brandDTO.setImageURL(brand.getImageURL());

        BeanUtils.copyProperties(brand, brandDTO);
        return  brandDTO;
    }

    public static Brand convertToEntity(BrandDTO brandDTO){
        Brand brand = new Brand();
        brand.setBrandId(brandDTO.getBrandId());
        brand.setBrandName(brandDTO.getBrandName());
        brand.setBrandDescription(brandDTO.getBrandDescription());
        brand.setTagLine(brandDTO.getTagLine());
        brand.setImageName(brandDTO.getImageName());
        brand.setImageURL(brandDTO.getImageURL());

        BeanUtils.copyProperties(brandDTO, brand);
        return brand;
    }
}
