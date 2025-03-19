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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "country_code")
public class CountryCode {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_name", nullable = false, length = 350)
    private String countryName;

    @Column(name = "country_code", nullable = false, length = 350)
    private String countryCode;

    @Column(name = "country_flag", nullable = false, length = 350)
    private String countryFlag;

    @Column(name = "status", nullable = false)
    private int status;
}
