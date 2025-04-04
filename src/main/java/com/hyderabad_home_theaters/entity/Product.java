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
    private String status;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", referencedColumnName = "sub_category_id")
    private SubCategory subCategory;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "original_price")
    private Double originalPrice; // ✅ Price can have decimals

    @Column(name = "discounted_price")
    private Double discountedPrice; // ❌ Change from String → Double

    @Column(name = "discount_percentage")
    private Double discountPercentage; // ❌ Change from String → Double (e.g., 10.5%)

    @Column(name = "tax_percentage")
    private Double taxPercentage; // ❌ Change from String → Double

    @Column(name = "currency")
    private String currency; // ✅ e.g., "INR", "USD"

    @Column(name = "color")
    private String color; // ✅ e.g., "Black", "Red"

    @Column(name = "size")
    private String size; // ✅ Can be "S", "M", "L", or dimensions like "12x12"

    @Column(name = "weight")
    private Double weight; // ❌ Change from String → Double (in kg, grams, etc.)

    @Column(name = "dimensions")
    private String dimensions; // ✅ If you're storing "10x10x5 cm", keep as String

    @Column(name = "material")
    private String material; // ✅ e.g., "Plastic", "Metal"

    @Column(name = "warranty_period")
    private Integer warrantyPeriod; // ❌ Change from String → Integer (e.g., 12 months)





}
