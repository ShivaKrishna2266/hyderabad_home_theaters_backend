package com.hyderabad_home_theaters.services.impl;


import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;
import com.hyderabad_home_theaters.mapper.BrandMapper;
import com.hyderabad_home_theaters.repository.BrandRepository;
import com.hyderabad_home_theaters.repository.CategoryRepository;
import com.hyderabad_home_theaters.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public BrandDTO getBrandById(Long brandId) {
        Optional<Brand> optionalBrand = brandRepository.findById(brandId);

        return optionalBrand.map(BrandMapper::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Brand not found with ID: " + brandId));
    }
    @Override
    public List<BrandDTO> getAllBrands() {
            List<Brand> brands = brandRepository.findAll();
        return brands
                .stream()
                .map(BrandMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDTO createBrand (BrandDTO brandDTO)throws ApplicationBusinessException {
        try {
            Brand brand = BrandMapper.convertToEntity(brandDTO, categoryRepository );
            brand.setBrandName(brandDTO.getBrandName());
            brand.setBrandDescription(brandDTO.getBrandDescription());
            brand.setTagLine(brandDTO.getTagLine());
            brand.setImageName(brandDTO.getImageName());
            brand.setImageURL(brandDTO.getImageURL());
            brand.setCreatedBy("System");
            brand.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            brand.setUpdatedBy("System");
            brand.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

            Category category = categoryRepository.findById(brandDTO.getCategoryId()) .orElseThrow(() -> new RuntimeException("category not found"));
            brand.setCategory(category);


            Brand savedBrand = brandRepository.save(brand);
            BrandDTO brandDTO1 = BrandMapper.convertToDTO(savedBrand);
            return  brandDTO1;
        }catch (Exception e){
            throw new ApplicationBusinessException("Business error occurred", e);
        }
    }

    @Override
    public BrandDTO updateBrand(Long brandId, BrandDTO brandDTO) {
        Optional<Brand> optionalBrand = brandRepository.findById(brandId);
        if (optionalBrand.isPresent()){
            Brand brand = optionalBrand.get();
            brand.setBrandName(brandDTO.getBrandName());
            brand.setBrandDescription(brandDTO.getBrandDescription());
            brand.setTagLine(brandDTO.getTagLine());
            brand.setImageName(brandDTO.getImageName());
            brand.setImageURL(brandDTO.getImageURL());
            brand.setStatus(brandDTO.getStatus());


            if (brandDTO.getCategoryId() != null) {
                Category category = categoryRepository.findById(brandDTO.getCategoryId()) .orElseThrow(() -> new RuntimeException("Category not found"));
                brand.setCategory(category);
            } else {
                System.out.println("Category ID is null in brandDTO");
            }

            brand.setCreatedBy("System");
            brand.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            brand.setUpdatedBy("System");
            brand.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
             Brand savedBrand =  brandRepository.save(brand);
             BrandDTO brandDTO1 = BrandMapper.convertToDTO(savedBrand);
             return  brandDTO1;

        }
        return null;
    }

    @Override
    public void deleteBrandById(Long brandId) {

    }

    @Override
    public BrandDTO updateBrandStatusById(Long brandId, String status) {
       Optional<Brand> brand = brandRepository.findById(brandId);
        if (brand.isPresent()){
            brand.get().setStatus(status);
            return BrandMapper.convertToDTO(brandRepository.save(brand.get()));
        }
        return  null;
    }
}