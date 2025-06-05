package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BannerDTO {

    private  Long bannerId;
    private String title;
    private String subTitle;
    private List<String> coverImage;
    private List<String> videoFileName;
    private String url;
    private String status;
    private String proStatus;

}
