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
@Table(name = "general_settings")
public class GeneralSettings {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "logo_dark")
    private String logoDark;

    @Column(name = "fav_icon")
    private String favIcon;

    @Column(name = "footer")
    private String footer;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "gst")
    private int gst;

    @Column(name = "logo_image_dark")
    private String logoImageDark;

    @Column(name = "fav_icon_image")
    private String favIconImage;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "color_code")
    private String colorCode;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

}
