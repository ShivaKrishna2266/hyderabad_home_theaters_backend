package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.CategoryDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.entity.SubCategory;
import com.hyderabad_home_theaters.repository.BrandRepository;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import com.hyderabad_home_theaters.repository.SubCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryMapper {

    @Autowired
    private static BrandRepository brandRepository;

    @Autowired
    private static SubCategoryRepository subCategoryRepository;
    public static final ModelMapper modelMapper = new ModelMapper();

    public static CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setTagline(category.getTagline());
        categoryDTO.setStatus(category.getStatus());
        categoryDTO.setBrandId(category.getBrand().getBrandId());
        categoryDTO.setImageUrl(category.getImageUrl());
//        categoryDTO.setSubCategoryId(category.getSubCategory().getSubCategoryId());

        return categoryDTO;
    }

    public static Category convertToEntity(CategoryDTO categoryDTO,
                                           BrandRepository brandRepository,
                                           SubCategoryRepository subCategoryRepository){
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());
        category.setTagline(categoryDTO.getTagline());
        category.setStatus(categoryDTO.getStatus());
        category.setImageUrl(categoryDTO.getImageUrl());
        Brand brand = brandRepository.findById(categoryDTO.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        category.setBrand(brand);

//        SubCategory subCategory = subCategoryRepository.findById(categoryDTO.getSubCategoryId()).
//                orElseThrow(() -> new RuntimeException("Sub Category is not found"));
//        category.setSubCategory(subCategory);

        return  category;
    }
}
