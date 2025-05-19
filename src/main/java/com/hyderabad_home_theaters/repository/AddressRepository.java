package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Address;
import com.hyderabad_home_theaters.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

//    List<Address> findByUserId(Long userId);

    List<Address> findByCustomerId(Long customerId);


    List<Address> findByUserProfile(UserProfile userProfile);





}