package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.ProfileDTO;
import com.hyderabad_home_theaters.DTOs.payment.OrderRequest;

public interface UserProfileService {

    ProfileDTO getUserByFirstName(String firstName);
    ProfileDTO createProfile(ProfileDTO profileDTO);
    void updateProfile(OrderRequest orderRequest);
}
