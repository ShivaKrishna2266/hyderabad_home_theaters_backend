package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    Optional<Banner> findByTitle(String title); // This creates the query automatically

}
