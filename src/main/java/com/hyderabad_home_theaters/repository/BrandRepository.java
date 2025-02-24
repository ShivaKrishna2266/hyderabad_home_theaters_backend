package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {

    @Query("SELECT cb FROM Brand cb WHERE cb.brandId = :brandId")
    List<Brand> findByCarBrandId(@Param("brandId") Long brandId);
}
