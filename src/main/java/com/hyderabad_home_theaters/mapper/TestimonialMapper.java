package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.TestimonialDTO;
import com.hyderabad_home_theaters.entity.Testimonial;
import org.modelmapper.ModelMapper;

public class TestimonialMapper {

    public static final ModelMapper modelMapper = new ModelMapper();

    public static TestimonialDTO convertToDTO(Testimonial testimonial) {
        return modelMapper.map(testimonial,TestimonialDTO.class);
    }

    public static Testimonial convertToEntity(TestimonialDTO testimonialDTO) {
        return modelMapper.map(testimonialDTO, Testimonial.class);
    }
}
