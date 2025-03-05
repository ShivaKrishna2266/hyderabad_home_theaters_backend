package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryDTO {

    private  Long subCategoryId;
    private String subCategoryName;
    private String description;
    private String tagline;
    private Long categoryId;
    private Long productId;
}
