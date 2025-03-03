package com.hyderabad_home_theaters.DTOs;

import com.hyderabad_home_theaters.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long categoryIg;
    private String categoryName;
    private String description;
    private String tagline;
    private Boolean status;
    private Brand brand;
}
