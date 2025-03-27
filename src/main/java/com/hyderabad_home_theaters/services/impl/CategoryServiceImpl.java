package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.CategoryDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.entity.SubCategory;
import com.hyderabad_home_theaters.mapper.CategoryMapper;
import com.hyderabad_home_theaters.repository.BrandRepository;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import com.hyderabad_home_theaters.repository.SubCategoryRepository;
import com.hyderabad_home_theaters.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> category = categoryRepository.findAll();
        return category.stream()
                .map(CategoryMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            return  CategoryMapper.convertToDTO(category);
        }else{
            return null;
        }
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        // Convert DTO to Entity
        Category category = CategoryMapper.convertToEntity(categoryDTO,brandRepository, subCategoryRepository);

        // Set Category attributes
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());
        category.setTagline(categoryDTO.getTagline());
        category.setStatus(categoryDTO.getStatus());
        category.setImageUrl(categoryDTO.getImageUrl());
        category.setCreatedBy("System");
        category.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        category.setUpdatedBy("System");
        category.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));


        Brand brand = brandRepository.findById(categoryDTO.getBrandId()) .orElseThrow(() -> new RuntimeException("Brand not found"));
        category.setBrand(brand);

//        SubCategory subCategory = subCategoryRepository.findById(categoryDTO.getSubCategoryId()).orElseThrow(() -> new RuntimeException("Sub Category is not found"));
//        category.setSubCategory(subCategory);

        Category savedCategory = categoryRepository.save(category);
        CategoryDTO categoryDTO1 = CategoryMapper.convertToDTO(savedCategory);
        return categoryDTO1;
    }


    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setDescription(categoryDTO.getDescription());
            category.setTagline(categoryDTO.getTagline());
            category.setStatus(categoryDTO.getStatus());
            category.setImageUrl(categoryDTO.getImageUrl());
            category.setCreatedBy("System");
            category.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            category.setUpdatedBy("System");
            category.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));


            if (categoryDTO.getBrandId() != null) {
                Brand brand = brandRepository.findById(categoryDTO.getBrandId()) .orElseThrow(() -> new RuntimeException("Brand not found"));
                category.setBrand(brand);
            } else {
                System.out.println("Brand ID is null in categoryDTO");
            }



            Category updatedCategory = categoryRepository.save(category);
            return CategoryMapper.convertToDTO(updatedCategory);
        }
        throw new RuntimeException("Category not found");
    }
    @Override
    public void deleteCategoryById(Long categoryId) {

    }
}
