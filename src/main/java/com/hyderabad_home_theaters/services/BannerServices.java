package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.BannerDTO;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;

import java.util.List;

public interface BannerServices {


    List<BannerDTO> getAllBanner();

    BannerDTO getBannerById(Long bannerId);

    BannerDTO addBanner(BannerDTO bannerDTO) throws ApplicationBusinessException;

    BannerDTO updateBannerById(Long bannerId,BannerDTO bannerDTO);

    BannerDTO deleteBannerById(Long bannerId);

    BannerDTO updateBannerStatus(Long bannerId, String status);
}
