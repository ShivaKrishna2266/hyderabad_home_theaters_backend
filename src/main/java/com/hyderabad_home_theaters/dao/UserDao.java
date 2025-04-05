package com.hyderabad_home_theaters.dao;

import com.hyderabad_home_theaters.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);
}
