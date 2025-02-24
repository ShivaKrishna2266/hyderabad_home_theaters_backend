package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.RoleDTO;
import com.hyderabad_home_theaters.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class RoleMapper {
    public static final ModelMapper modelMapper =new ModelMapper();

    public static RoleDTO convertedToDTO(Role role){
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(role, roleDTO);
        return roleDTO;
    }

    public static Role convertedToEntity(RoleDTO roleDTO){
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        return  role;
    }
}
