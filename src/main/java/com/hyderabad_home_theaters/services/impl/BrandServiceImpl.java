package com.hyderabad_home_theaters.services.impl;


import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;
import com.hyderabad_home_theaters.mapper.BrandMapper;
import com.hyderabad_home_theaters.repository.BrandRepository;
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


    @Override
    public BrandDTO getBrandById(Long brandId) {
        Optional<Brand> optionalBrand =brandRepository.findById(brandId);
        if(optionalBrand.isPresent()){
            Brand brand = optionalBrand.get();
            return  BrandMapper.convertToDTO(brand);
        }else {
            return null;
        }
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
    public BrandDTO createBrand(BrandDTO brandDTO)throws ApplicationBusinessException {
        try {
            Brand brand = BrandMapper.convertToEntity(brandDTO);
            brand.setBrandName(brandDTO.getBrandName());
            brand.setBrandDescription(brandDTO.getBrandDescription());
            brand.setTagLine(brandDTO.getTagLine());
            brand.setImageName(brandDTO.getImageName());
            brand.setImageURL(brandDTO.getImageURL());
            brand.setCreatedBy("System");
            brand.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            brand.setUpdatedBy("System");
            brand.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
            Brand savedBrand = brandRepository.save(brand);
            BrandDTO brandDTO1 = BrandMapper.convertToDTO(savedBrand);
            return brandDTO1;
        }catch (Exception e){
            throw new ApplicationBusinessException("Business error occurred", e);
        }
    }

    @Override
    public BrandDTO updateBrand(Long brandId, BrandDTO brandDTO) {
        return null;
    }

    @Override
    public void deleteBrandById(Long brandId) {

    }
}