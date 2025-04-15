package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long productId);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
    void deleteProductById(Long productId);

    public List<ProductDTO> getProductsByBrand(Long brandId);
    public List<ProductDTO> getProductsByCategory(Long categoryId);
}
