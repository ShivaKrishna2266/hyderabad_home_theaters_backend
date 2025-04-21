package com.hyderabad_home_theaters.repository;


import com.hyderabad_home_theaters.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findByEmail(String email);

    UserProfile findByUsername(String username);
}
