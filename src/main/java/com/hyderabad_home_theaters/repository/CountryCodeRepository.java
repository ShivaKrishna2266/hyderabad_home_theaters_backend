package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryCodeRepository extends JpaRepository<CountryCode , Long> {
}
