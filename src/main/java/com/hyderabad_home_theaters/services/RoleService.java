package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.RoleDTO;
import com.hyderabad_home_theaters.entity.Role;

import java.util.List;

public interface RoleService {

    Role findByRoleName( String roleName);

    List<RoleDTO> getAllRoles();
}
