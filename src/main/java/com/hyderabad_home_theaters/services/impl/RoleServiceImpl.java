package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.RoleDTO;
import com.hyderabad_home_theaters.entity.Role;
import com.hyderabad_home_theaters.mapper.RoleMapper;
import com.hyderabad_home_theaters.repository.RoleRepository;
import com.hyderabad_home_theaters.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public List<RoleDTO> getAllRoles() {

       List<Role> roles = roleRepository.findAll();
        return roles
                .stream()
                .map(r -> RoleMapper.convertedToDTO(r))
                .collect(Collectors.toList());
    }
}
