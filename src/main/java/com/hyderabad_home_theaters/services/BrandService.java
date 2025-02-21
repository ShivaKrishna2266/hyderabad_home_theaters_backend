package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.BrandDTO;

import java.util.List;

public interface BrandService {

    BrandDTO getBrandById(Long brandId);
    List<BrandDTO> getAllBrands();
    BrandDTO createBrand (BrandDTO brandDTO);
    BrandDTO updateBrand (Long brandId, BrandDTO brandDTO);
    void deleteBrandById(Long brandId);
}
