package com.hyderabad_home_theaters.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "banner")
public class Banner {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private  Long bannerId;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "url")
    private String url;

    @Column(name = "status")
    private String status;

    @Column(name = "pro_status")
    private String proStatus;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updated_date")
    private Timestamp updatedDate;
}
