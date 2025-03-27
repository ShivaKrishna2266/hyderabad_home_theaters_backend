package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long categoryId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

    void deleteCategoryById(Long categoryId);

}
