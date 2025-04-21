package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);

//    User findByRole(UserRole userRole);
}
