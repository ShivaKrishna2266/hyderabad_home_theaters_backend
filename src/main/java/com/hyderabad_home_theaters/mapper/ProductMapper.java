package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.ProductDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.entity.Product;
import com.hyderabad_home_theaters.entity.SubCategory;
import com.hyderabad_home_theaters.repository.BrandRepository;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import com.hyderabad_home_theaters.repository.SubCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductMapper {

    @Autowired
    private static BrandRepository brandRepository;
    @Autowired
    private static CategoryRepository categoryRepository;

    @Autowired
    private static SubCategoryRepository subCategoryRepository;

    public static final ModelMapper modelMapper = new ModelMapper();

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

        productDTO.setBrandId(product.getBrand().getBrandId());
        productDTO.setCategoryId(product.getCategory().getCategoryId());
        productDTO.setSubCategoryId(product.getSubCategory().getSubCategoryId());

        return  productDTO;
    }

    public static Product convertToEntity(ProductDTO productDTO){
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
