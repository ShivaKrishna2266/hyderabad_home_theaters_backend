package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.BannerDTO;
import com.hyderabad_home_theaters.DTOs.BrandDTO;
import com.hyderabad_home_theaters.entity.Banner;
import com.hyderabad_home_theaters.entity.Brand;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;
import com.hyderabad_home_theaters.mapper.BannerMapper;
import com.hyderabad_home_theaters.mapper.BrandMapper;
import com.hyderabad_home_theaters.repository.BannerRepository;
import com.hyderabad_home_theaters.services.BannerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BannerServicesImpl implements BannerServices {

    @Autowired
    private BannerRepository bannerRepository;



    @Override
    public List<BannerDTO> getAllBanner() {
        List<Banner> banner = bannerRepository.findAll();
       return  banner.stream()
               .map(BannerMapper ::convertToDTO)
               .collect(Collectors.toList());
    }

    @Override
    public BannerDTO getBannerById(Long bannerId) {
        Optional<Banner> optionalBanner = bannerRepository.findById(bannerId);

        return optionalBanner.map(BannerMapper::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Banner not found with ID: " + bannerId));
    }

    @Override
    public BannerDTO addBanner(BannerDTO bannerDTO) throws ApplicationBusinessException {
        try {
            Banner banner = BannerMapper.convertToEntity(bannerDTO);

            banner.setTitle(banner.getTitle());
            banner.setCoverImage(banner.getCoverImage());
            banner.setSubTitle(banner.getSubTitle());
            banner.setStatus(banner.getStatus());
            banner.setProStatus(banner.getProStatus());
            banner.setUrl(banner.getUrl());

            banner.setCreatedBy("System");
            banner.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            banner.setUpdatedBy("System");
            banner.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

            Banner savedBanner = bannerRepository.save(banner);
            BannerDTO bannerDTO1 = BannerMapper.convertToDTO(savedBanner);
            return  bannerDTO1;

        } catch (Exception e) {
            throw new ApplicationBusinessException("Business error occurred", e);
        }
    }

    @Override
    public BannerDTO updateBannerById(Long bannerId, BannerDTO bannerDTO) {
        Optional<Banner> optionalBanner = bannerRepository.findById(bannerId);
        if (optionalBanner.isPresent()){

            Banner banner = optionalBanner.get();

            banner.setTitle(banner.getTitle());
            banner.setSubTitle(banner.getSubTitle());
            banner.setCoverImage(banner.getCoverImage());
            banner.setStatus(banner.getStatus());
            banner.setProStatus(banner.getProStatus());

            banner.setCreatedBy("System");
            banner.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            banner.setUpdatedBy("System");
            banner.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

            Banner savedBanner =  bannerRepository.save(banner);
            BannerDTO bannerDTO1 = BannerMapper.convertToDTO(savedBanner);
            return  bannerDTO1;

        }
        return null;
    }

    @Override
    public BannerDTO deleteBannerById(Long bannerId) {
        try {
            Banner bannerToDelete = bannerRepository.findById(bannerId)
                    .orElseThrow(()-> new ApplicationBusinessException("Banner id not found"));
            bannerRepository.deleteById(bannerId);
            return BannerMapper.convertToDTO(bannerToDelete);
        }catch (Exception e){
            throw  new ApplicationBusinessException("Error while delete banner status");
        }
    }

    @Override
    public BannerDTO updateBannerStatus(Long bannerId, String status) {
        try {
            Optional<Banner> banner = bannerRepository.findById(bannerId);
            if (banner.isPresent()) {
                banner.get().setStatus(status);
                return BannerMapper.convertToDTO(bannerRepository.save(banner.get()));
            }
            return null;
        }catch (Exception e){
            throw  new ApplicationBusinessException("Error while update banner status");
        }
    }
}
