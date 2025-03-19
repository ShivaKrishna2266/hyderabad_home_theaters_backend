package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralSettingsDTO {

    private Long id;
    private String logoDark ;
    private String favIcon;
    private String footer;
    private String phoneNumber;
    private String smtpUser; // Add smtpUser field
    private String companyName;
    private int gst;
    private String logoImageDark;
    private String favIconImage;
    private String address;
    private String email;
    private String colorCode;
}
