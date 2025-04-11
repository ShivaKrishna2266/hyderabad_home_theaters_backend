package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.ProductDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.entity.Product;
import com.hyderabad_home_theaters.entity.Review;
import com.hyderabad_home_theaters.entity.SubCategory;
import com.hyderabad_home_theaters.repository.BrandRepository;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import com.hyderabad_home_theaters.repository.ReviewRepository;
import com.hyderabad_home_theaters.repository.SubCategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static ProductDTO convertToDTO(Product product) {
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

        productDTO.setOriginalPrice(product.getOriginalPrice());
        productDTO.setDiscountedPrice(product.getDiscountedPrice());
        productDTO.setDiscountPercentage(product.getDiscountPercentage());
        productDTO.setTaxPercentage(product.getTaxPercentage());
        productDTO.setCurrency(product.getCurrency());
        productDTO.setColor(product.getColor());
        productDTO.setSize(product.getSize());
        productDTO.setWeight(product.getWeight());
        productDTO.setDimensions(product.getDimensions());
        productDTO.setMaterial(product.getMaterial());
        productDTO.setWarrantyPeriod(product.getWarrantyPeriod());
        productDTO.setDescription(product.getDescription());

        if (product.getBrand() != null) {
            productDTO.setBrandId(product.getBrand().getBrandId());
        }
        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getCategoryId());
        }
        if (product.getSubCategory() != null) {
            productDTO.setSubCategoryId(product.getSubCategory().getSubCategoryId());
        }
        if (product.getReview() != null){
            productDTO.setReviewId(product.getReview().getReviewId());
        }

        return productDTO;
    }

    public static Product convertToEntity(ProductDTO productDTO,
                                          BrandRepository brandRepository,
                                          CategoryRepository categoryRepository,
                                          SubCategoryRepository subCategoryRepository,
                                          ReviewRepository reviewRepository) {

        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductRank(productDTO.getProductRank());
        product.setProductSku(productDTO.getProductSku());
        product.setImageName(productDTO.getImageName());
        product.setImageURL(productDTO.getImageURL());
        product.setStockQuantity(productDTO.getStockQuantity());

        product.setOriginalPrice(productDTO.getOriginalPrice());
        product.setDiscountedPrice(productDTO.getDiscountedPrice());
        product.setDiscountPercentage(productDTO.getDiscountPercentage());
        product.setTaxPercentage(productDTO.getTaxPercentage());
        product.setCurrency(productDTO.getCurrency());
        product.setColor(productDTO.getColor());
        product.setSize(productDTO.getSize());
        product.setWeight(productDTO.getWeight());
        product.setDimensions(productDTO.getDimensions());
        product.setMaterial(productDTO.getMaterial());
        product.setWarrantyPeriod(productDTO.getWarrantyPeriod());
        product.setDescription(productDTO.getDescription());

        if (productDTO.getBrandId() != null) {
            Brand brand = brandRepository.findById(productDTO.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand ID not found"));
            product.setBrand(brand);
        }

        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category ID not found"));
            product.setCategory(category);
        }

        if (productDTO.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(productDTO.getSubCategoryId())
                    .orElseThrow(() -> new RuntimeException("SubCategory ID not found"));
            product.setSubCategory(subCategory);
        }

        if(productDTO.getReviewId() != null){
            Review review = reviewRepository.findById(productDTO.getReviewId()).orElseThrow( () ->new RuntimeException("Review ID not found"));
            product.setReview(review);
        }

        return product;
    }
}
