package com.hyderabad_home_theaters.repository;

import com.hyderabad_home_theaters.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role , Long> {

    Role findByRoleName(String roleName);
}
