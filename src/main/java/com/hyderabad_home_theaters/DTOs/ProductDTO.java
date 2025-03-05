package com.hyderabad_home_theaters.DTOs;

import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long productId;
    private String productName;
    private Integer stockQuantity;
    private Boolean status;
    private Integer productPrice;
    private Integer productRank;
    private String productSku;
    private String imageName;
    private String  imageURL;
    private Long categoryId;
    private Long brandId;
    private Long subCategoryId;

}
