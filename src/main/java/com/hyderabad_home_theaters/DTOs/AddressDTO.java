package com.hyderabad_home_theaters.DTOs;

import com.hyderabad_home_theaters.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddressDTO {
    private Long addressId;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String area;
    private String city;
    private String pinCode;
    private String state;
    private String country;
    private String region;
    private UserProfile userProfile;
    private Long customerId;

}
