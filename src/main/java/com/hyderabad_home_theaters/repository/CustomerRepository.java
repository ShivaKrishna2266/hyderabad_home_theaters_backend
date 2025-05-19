package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByEmail(String email);

    Customer findByFirstName(String firstName);

    Optional<Customer> findByUsername(String username);

}
