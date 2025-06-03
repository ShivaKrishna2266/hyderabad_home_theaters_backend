package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BannerDTO {

    private  Long bannerId;
    private String title;
    private String subTitle;
    private String coverImage;
    private String url;
    private String status;
    private String proStatus;

}
