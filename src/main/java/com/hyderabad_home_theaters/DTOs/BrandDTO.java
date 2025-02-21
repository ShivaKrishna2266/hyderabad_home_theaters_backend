package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

    private Long brandId;
    private String brandName;
    private String brandDescription;
    private String tagLine;
    private String imageName;
    private String imageURL;
    private String createdBy;
    private Timestamp createdDate;
    private String updatedBy;
    private Timestamp updatedDate;

}
