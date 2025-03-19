package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.ContactUsDTO;
import com.hyderabad_home_theaters.entity.ContactUs;
import org.modelmapper.ModelMapper;

public class ContactUsMapper {

    public static final ModelMapper modelMapper = new ModelMapper();

    public static ContactUsDTO convertToDTO(ContactUs contactUs){
        return modelMapper.map(contactUs, ContactUsDTO.class);
    }

    public static ContactUs convertTOEntity(ContactUsDTO contactUsDTO){
        return modelMapper.map(contactUsDTO, ContactUs.class);
    }
}
