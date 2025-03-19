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

@Entity
@Table(name = "contact_us")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUs {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 250)
    private String name;

    @Column(name = "email_id", length = 450)
    private String emailId;

    @Column(name = "phone_no", length = 250)
    private String phoneNo;

    @Column(name = "country_code" )
    private Integer countryCode;

    @Column(name = "message")
    private String message;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "lead_status")
    private String leadStatus;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_by")
    private String createdBy;
}
