package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.SubCategoryDTO;

import java.util.List;

public interface SubCategoryService {

    List<SubCategoryDTO> getAllSubCategories();

    SubCategoryDTO getSubcategoryById (Long  subCategoryId);

    SubCategoryDTO createSubCategory( SubCategoryDTO subCategoryDTO);

    SubCategoryDTO updateSubCategory(Long subcategoryId, SubCategoryDTO subCategoryDTO);

    void deleteSubCategoryById(Long subCategoryId);
}
