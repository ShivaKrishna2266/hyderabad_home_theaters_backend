package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

//    List<Address> findByUserId(Long userId);

    List<Address> findByCustomerId(Long customerId);
}