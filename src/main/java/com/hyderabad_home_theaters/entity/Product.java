package com.hyderabad_home_theaters.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "product_rice")
    private Integer productPrice;

    @Column(name = "product_rank")
    private Integer productRank;

    @Column(name = "productSku")
    private String productSku;

    @Column(name = "image_name")
    private String imageName;

    @Column(name ="image_URL")
    private String  imageURL;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updated_date")
    private Timestamp updatedDate;


}
