package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long productId;
    private String productName;
    private Integer stockQuantity;
    private String status;
    private Integer productPrice;
    private Integer productRank;
    private String productSku;
    private String imageName;
    private List<String> images;
    private Long categoryId;
    private Long brandId;
    private Long subCategoryId;
    private Long reviewId;

    private Double originalPrice;
    private Double discountedPrice;
    private Double discountPercentage;
    private Double taxPercentage;
    private String currency;
    private String color;
    private String size;
    private Double weight;
    private String dimensions;
    private String material;
    private Integer warrantyPeriod;
    private String description;

}
