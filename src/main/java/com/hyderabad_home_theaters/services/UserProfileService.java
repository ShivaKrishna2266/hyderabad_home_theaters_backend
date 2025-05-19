package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.ProfileDTO;

public interface UserProfileService {

    ProfileDTO getUserByFirstName(String firstName);
    ProfileDTO createProfile(ProfileDTO profileDTO);
    ProfileDTO updateProfile(String email, ProfileDTO profileDTO);
}
