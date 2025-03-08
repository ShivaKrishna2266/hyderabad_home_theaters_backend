package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.ProductDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.entity.Product;
import com.hyderabad_home_theaters.entity.SubCategory;
import com.hyderabad_home_theaters.repository.BrandRepository;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import com.hyderabad_home_theaters.repository.SubCategoryRepository;

public class ProductMapper {

//    public static final ModelMapper modelMapper = new ModelMapper();

    public static ProductDTO convertToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setProductName(product.getProductName());
        productDTO.setProductPrice(product.getProductPrice());
        productDTO.setProductRank(product.getProductRank());
        productDTO.setProductSku(product.getProductSku());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setImageName(product.getImageName());
        productDTO.setImageURL(product.getImageURL());
        productDTO.setStatus(product.getStatus());

        if (product.getBrand() != null) {
            productDTO.setBrandId(product.getBrand().getBrandId());
        }
        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getCategoryId());
        }
        if (product.getSubCategory() != null) {
            productDTO.setSubCategoryId(product.getSubCategory().getSubCategoryId());
        }
        return  productDTO;
    }

    public static Product convertToEntity(ProductDTO productDTO,
                                          BrandRepository brandRepository,
                                          CategoryRepository categoryRepository,
                                          SubCategoryRepository subCategoryRepository){
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductRank(productDTO.getProductRank());
        product.setProductSku(productDTO.getProductSku());
        product.setImageName(productDTO.getImageName());
        product.setImageURL(productDTO.getImageURL());
        product.setStockQuantity(productDTO.getStockQuantity());

        Brand brand = brandRepository.findById(productDTO.getBrandId()).orElseThrow(() -> new RuntimeException("Brand id is not found"));
         product.setBrand(brand);

        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category id is not found"));
         product.setCategory(category);

        SubCategory subCategory = subCategoryRepository.findById(productDTO.getSubCategoryId()).orElseThrow(() -> new RuntimeException("SubCategory id is not found"));
        product.setSubCategory(subCategory);

        return product;
    }
}
