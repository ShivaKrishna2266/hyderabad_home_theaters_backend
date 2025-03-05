package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long > {
}
