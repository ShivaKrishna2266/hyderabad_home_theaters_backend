package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.ProductDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.entity.Product;
import com.hyderabad_home_theaters.entity.SubCategory;
import com.hyderabad_home_theaters.exception.ResourceNotFoundException;
import com.hyderabad_home_theaters.mapper.ProductMapper;
import com.hyderabad_home_theaters.repository.BrandRepository;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import com.hyderabad_home_theaters.repository.ProductRepository;
import com.hyderabad_home_theaters.repository.ReviewRepository;
import com.hyderabad_home_theaters.repository.SubCategoryRepository;
import com.hyderabad_home_theaters.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
           return products
                   .stream()
                   .map(ProductMapper::convertToDTO)
                   .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            return ProductMapper.convertToDTO(product);
        }else {
            return null;
        }
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        // Convert DTO to Entity (includes mapping Brand, Category, SubCategory)
        Product product = ProductMapper.convertToEntity(productDTO, brandRepository, categoryRepository, subCategoryRepository, reviewRepository);

        // Set additional fields (in case not handled in mapper)
        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductSku(productDTO.getProductSku());
        product.setProductRank(productDTO.getProductRank());
        product.setStockQuantity(productDTO.getStockQuantity());

        // Set extra detailed product fields
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

        // Set image and status
        product.setImageName(productDTO.getImageName());
        product.setImageURL(productDTO.getImageURL());
        product.setStatus(productDTO.getStatus());

        // Set audit info
        product.setCreatedBy("System");
        product.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        product.setUpdatedBy("System");
        product.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

        // Set associations (only if not already set inside mapper)
        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found with ID: " + productDTO.getBrandId()));
        product.setBrand(brand);

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + productDTO.getCategoryId()));
        product.setCategory(category);

        SubCategory subCategory = subCategoryRepository.findById(productDTO.getSubCategoryId())
                .orElseThrow(() -> new RuntimeException("SubCategory not found with ID: " + productDTO.getSubCategoryId()));
        product.setSubCategory(subCategory);

        // Save the product to the database
        Product savedProduct = productRepository.save(product);

        // Convert saved entity back to DTO
        return ProductMapper.convertToDTO(savedProduct);
    }


    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // Update core product fields
            product.setProductName(productDTO.getProductName());
            product.setProductRank(productDTO.getProductRank());
            product.setProductPrice(productDTO.getProductPrice());
            product.setProductSku(productDTO.getProductSku());
            product.setStatus(productDTO.getStatus());
            product.setStockQuantity(productDTO.getStockQuantity());
            product.setImageName(productDTO.getImageName());
            product.setImageURL(productDTO.getImageURL());

            // Update additional fields
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



            // Update timestamps and audit fields
            product.setUpdatedBy("System");
            product.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

            // Set brand, category, and subcategory
            Brand brand = brandRepository.findById(productDTO.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);

            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);

            SubCategory subCategory = subCategoryRepository.findById(productDTO.getSubCategoryId())
                    .orElseThrow(() -> new RuntimeException("SubCategory Id is not found"));
            product.setSubCategory(subCategory);

            // Save and return updated product
            Product updatedProduct = productRepository.save(product);
            return ProductMapper.convertToDTO(updatedProduct);
        }

        throw new RuntimeException("Product with ID " + productId + " not found.");
    }

    @Override
    public List<ProductDTO> getProductsByBrand(Long brandId) {
        List<Product> products = productRepository.findByBrandId(brandId);
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product : products) {
            ProductDTO dto = ProductMapper.convertToDTO(product);
            productDTOS.add(dto);
        }
        return productDTOS.isEmpty() ? Collections.emptyList() : productDTOS;
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product : products) {
            ProductDTO dto = ProductMapper.convertToDTO(product);
            productDTOS.add(dto);
        }
        return productDTOS.isEmpty() ? Collections.emptyList() : productDTOS;
    }


    @Override
    public void deleteProductById(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product with ID " + productId + " not found");
        }
        productRepository.deleteById(productId);
    }

}
