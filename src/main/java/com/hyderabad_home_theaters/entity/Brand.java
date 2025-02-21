package com.hyderabad_home_theaters.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "brand_id")
    private  Long brandId;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "brand_description")
    private String brandDescription;

    @Column(name = "tagLine")
    private String tagLine;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_URL")
    private String imageURL;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Timestamp updatedDate;
}
