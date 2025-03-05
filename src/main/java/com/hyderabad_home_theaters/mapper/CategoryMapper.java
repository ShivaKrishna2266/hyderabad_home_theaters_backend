package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.CategoryDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryMapper {

    @Autowired
    private static BrandRepository brandRepository;
    public static final ModelMapper modelMapper = new ModelMapper();

    public static CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setTagline(category.getTagline());
        categoryDTO.setBrandId(category.getBrand().getBrandId());
        return categoryDTO;
    }

    public static Category convertToEntity(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());
        category.setTagline(categoryDTO.getTagline());
        Brand brand = brandRepository.findById(categoryDTO.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        category.setBrand(brand);
        return  category;
    }
}
