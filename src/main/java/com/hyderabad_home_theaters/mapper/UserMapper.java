package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.UserDTO;
import com.hyderabad_home_theaters.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class UserMapper {
    public static final ModelMapper modelMapper = new ModelMapper();

    public static UserDTO convertedToDTO(User user){
        UserDTO userDTO1 = new UserDTO();
        BeanUtils.copyProperties(user, userDTO1);
        return userDTO1;
    }

    public static User convertToEntity(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        return  user;
    }
}
