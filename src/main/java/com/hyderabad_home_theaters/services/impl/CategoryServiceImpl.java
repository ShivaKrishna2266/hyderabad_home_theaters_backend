package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.CategoryDTO;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.mapper.CategoryMapper;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import com.hyderabad_home_theaters.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

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
        return null;
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public void deleteCategoryById(Long categoryId) {

    }
}
