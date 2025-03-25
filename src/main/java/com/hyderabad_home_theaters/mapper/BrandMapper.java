package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    private static final ModelMapper modelMapper = new ModelMapper();



    public static BrandDTO convertToDTO(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();

        brandDTO.setBrandId(brand.getBrandId());
        brandDTO.setBrandName(brand.getBrandName());
        brandDTO.setBrandDescription(brand.getBrandDescription());
        brandDTO.setTagLine(brand.getTagLine());
        brandDTO.setImageName(brand.getImageName());
        brandDTO.setImageURL(brand.getImageURL());
        brandDTO.setStatus(brand.getStatus());

        if (brand.getCategory() != null) {
            brandDTO.setCategoryId(brand.getCategory().getCategoryId());
        }

        return brandDTO;
    }

    public static Brand convertToEntity(BrandDTO brandDTO,CategoryRepository categoryRepository) {
        Brand brand = new Brand();

        brand.setBrandId(brandDTO.getBrandId());
        brand.setBrandName(brandDTO.getBrandName());
        brand.setBrandDescription(brandDTO.getBrandDescription());
        brand.setTagLine(brandDTO.getTagLine());
        brand.setImageName(brandDTO.getImageName());
        brand.setImageURL(brandDTO.getImageURL());
        brand.setStatus(brandDTO.getStatus());

        if (brandDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(brandDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            brand.setCategory(category);
        }

        return brand;
    }
}
