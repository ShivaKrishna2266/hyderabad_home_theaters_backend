package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.SubCategoryDTO;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.entity.Product;
import com.hyderabad_home_theaters.entity.SubCategory;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import com.hyderabad_home_theaters.repository.ProductRepository;

public class SubCategoryMapper {

    public static SubCategoryDTO convertToDTO(SubCategory subCategory) {
        SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
        subCategoryDTO.setSubCategoryId(subCategory.getSubCategoryId());
        subCategoryDTO.setSubCategoryName(subCategory.getSubCategoryName());
        subCategoryDTO.setDescription(subCategory.getDescription());
        subCategoryDTO.setTagline(subCategory.getTagline());

        if (subCategory.getCategory() != null) {
            subCategoryDTO.setCategoryId(subCategory.getCategory().getCategoryId());
        }
        if (subCategory.getProduct() != null) {
            subCategoryDTO.setProductId(subCategory.getProduct().getProductId());
        }

        return subCategoryDTO;
    }

    public static SubCategory convertToEntity(SubCategoryDTO subCategoryDTO,
                                              CategoryRepository categoryRepository,
                                              ProductRepository productRepository) {
        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryId(subCategoryDTO.getSubCategoryId());
        subCategory.setSubCategoryName(subCategoryDTO.getSubCategoryName());
        subCategory.setDescription(subCategoryDTO.getDescription());
        subCategory.setTagline(subCategoryDTO.getTagline());

        Category category = categoryRepository.findById(subCategoryDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        subCategory.setCategory(category);

        Product product = productRepository.findById(subCategoryDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product is not found"));
        subCategory.setProduct(product);

        return subCategory;
    }
}
