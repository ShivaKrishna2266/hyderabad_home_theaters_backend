package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long categoryId;
    private String categoryName;
    private String description;
    private String tagline;
    private String status;
    private String imageUrl;
    private Long brandId;
    private Long subCategoryId;
}
