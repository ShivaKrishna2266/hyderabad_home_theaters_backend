package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.ProductDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.entity.Product;
import com.hyderabad_home_theaters.entity.SubCategory;
import com.hyderabad_home_theaters.mapper.ProductMapper;
import com.hyderabad_home_theaters.repository.BrandRepository;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import com.hyderabad_home_theaters.repository.ProductRepository;
import com.hyderabad_home_theaters.repository.SubCategoryRepository;
import com.hyderabad_home_theaters.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        Product product = ProductMapper.convertToEntity(productDTO);

        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductSku(productDTO.getProductSku());
        product.setProductRank(productDTO.getProductRank());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setImageName(product.getProductName());
        product.setImageURL(productDTO.getImageURL());
        product.setStatus(productDTO.getStatus());
        product.setCreatedBy("System");
        product.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        product.setUpdatedBy("System");
        product.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

        Brand brand = brandRepository.findById(productDTO.getBrandId()).orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);

        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setCategory(category);

        SubCategory subCategory = subCategoryRepository.findById(productDTO.getSubCategoryId()).orElseThrow(() -> new RuntimeException("SubCategory Id is not found"));
         product.setSubCategory(subCategory);

        Product savedProduct = productRepository.save(product);
        ProductDTO productDTO1 = ProductMapper.convertToDTO(savedProduct);
        return  productDTO1;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setProductName(productDTO.getProductName());
            product.setProductRank(productDTO.getProductRank());
            product.setProductPrice(productDTO.getProductPrice());
            product.setProductSku(productDTO.getProductSku());
            product.setStatus(productDTO.getStatus());
            product.setStockQuantity(productDTO.getStockQuantity());
            product.setImageName(productDTO.getImageName());
            product.setImageURL(productDTO.getImageURL());
            product.setCreatedBy("System");
            product.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            product.setUpdatedBy("System");
            product.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

            Brand brand = brandRepository.findById(productDTO.getBrandId()).orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);

            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setCategory(category);

            SubCategory subCategory = subCategoryRepository.findById(productDTO.getSubCategoryId()).orElseThrow(() -> new RuntimeException("SubCategory Id is not found"));
            product.setSubCategory(subCategory);

            Product updatedProduct = productRepository.save(product);
            ProductDTO productDTO1 = ProductMapper.convertToDTO(updatedProduct);
            return  productDTO1;

        }
        return  null;
    }

    @Override
    public void deleteProductById(Long productId) {

    }
}
