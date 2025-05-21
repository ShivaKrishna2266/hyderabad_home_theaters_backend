package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

    // Check if both username and phone number exist together
    boolean existsByUsernameAndPhoneNumber(String username, String phoneNumber);


    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM user WHERE user_name = :username AND password = :password", nativeQuery = true)
    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
