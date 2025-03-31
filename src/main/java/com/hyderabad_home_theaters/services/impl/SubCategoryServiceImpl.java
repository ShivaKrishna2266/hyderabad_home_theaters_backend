package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.ProductDTO;
import com.hyderabad_home_theaters.DTOs.SubCategoryDTO;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.entity.Product;
import com.hyderabad_home_theaters.entity.SubCategory;
import com.hyderabad_home_theaters.mapper.ProductMapper;
import com.hyderabad_home_theaters.mapper.SubCategoryMapper;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import com.hyderabad_home_theaters.repository.ProductRepository;
import com.hyderabad_home_theaters.repository.SubCategoryRepository;
import com.hyderabad_home_theaters.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<SubCategoryDTO> getAllSubCategories() {
        return subCategoryRepository.findAll()
                .stream()
                .map(SubCategoryMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public SubCategoryDTO getSubcategoryById(Long subCategoryId) {
        return subCategoryRepository.findById(subCategoryId)
                .map(SubCategoryMapper::convertToDTO)
                .orElse(null);
    }

    @Override
    public SubCategoryDTO createSubCategory(SubCategoryDTO subCategoryDTO) {
        SubCategory subCategory = SubCategoryMapper.convertToEntity(subCategoryDTO, categoryRepository, productRepository);
        subCategory.setCreatedBy("System");
        subCategory.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        subCategory.setUpdatedBy("System");
        subCategory.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

        Product product = productRepository.findById(subCategoryDTO.getProductId()).orElse(null);
        if (product != null) {
            subCategory.setProduct(product);
        }

        Category category = categoryRepository.findById(subCategoryDTO.getCategoryId()).orElse(null);
        if (category != null) {
            subCategory.setCategory(category);
        }

        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        return SubCategoryMapper.convertToDTO(savedSubCategory);
    }

    @Override
    public SubCategoryDTO updateSubCategory(Long subcategoryId, SubCategoryDTO subCategoryDTO) {
        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(subcategoryId);
        if (optionalSubCategory.isPresent()) {
            SubCategory subCategory = optionalSubCategory.get();
            subCategory.setSubCategoryName(subCategoryDTO.getSubCategoryName());
            subCategory.setDescription(subCategoryDTO.getDescription());
            subCategory.setTagline(subCategoryDTO.getTagline()); // Fixed issue here
            subCategory.setUpdatedBy("System");
            subCategory.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

            Product product = productRepository.findById(subCategoryDTO.getProductId()).orElse(null);
            if (product != null) {
                subCategory.setProduct(product);
            }

            Category category = categoryRepository.findById(subCategoryDTO.getCategoryId()).orElse(null);
            if (category != null) {
                subCategory.setCategory(category);
            }

            SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
            SubCategoryDTO subCategoryDTO1 = SubCategoryMapper.convertToDTO(savedSubCategory);
            return subCategoryDTO1;
        }
        return  null;
    }

    @Override
    public void deleteSubCategoryById(Long subCategoryId) {
        if (subCategoryRepository.existsById(subCategoryId)) {
            subCategoryRepository.deleteById(subCategoryId);
        } else {
            throw new RuntimeException("SubCategory not found with ID: " + subCategoryId);
        }
    }

    @Override
    public List<SubCategoryDTO> getSubCategoryByCategory(Long categoryId) {
        List<SubCategory> subCategories =subCategoryRepository.findByCategoryId(categoryId);
        List<SubCategoryDTO> subCategoryDTOS = new ArrayList<>();

        for(SubCategory subCategory : subCategories){
            SubCategoryDTO dto = SubCategoryMapper.convertToDTO(subCategory);
            subCategoryDTOS.add(dto);
        }
        return  subCategoryDTOS.isEmpty() ? Collections.emptyList() : subCategoryDTOS;
    }

}
