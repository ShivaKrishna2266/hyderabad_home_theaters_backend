package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.TestimonialDTO;
import com.hyderabad_home_theaters.constants.AppConstants;
import com.hyderabad_home_theaters.entity.Testimonial;
import com.hyderabad_home_theaters.entity.User;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;
import com.hyderabad_home_theaters.mapper.TestimonialMapper;
import com.hyderabad_home_theaters.repository.TestimonialRepository;
import com.hyderabad_home_theaters.services.TestimonialService;
import com.hyderabad_home_theaters.services.UserDetailsService;
import com.hyderabad_home_theaters.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public TestimonialDTO getTestimonialById(Long testimonialId) {
        Optional<Testimonial> findById = testimonialRepository.findById(testimonialId);
        if ( findById.isPresent()){
            return TestimonialMapper.convertToDTO(findById.get());
        }
        return  null;

    }

    @Override
    public List<TestimonialDTO> getAllTestimonials() {
        List<Testimonial> testimonials = testimonialRepository.findAll();
        List<TestimonialDTO> testimonialDTOS =new ArrayList<>();
        for (Testimonial testimonial : testimonials){
            testimonialDTOS.add(TestimonialMapper.convertToDTO(testimonial));
        }
        return testimonialDTOS;
    }

    @Override
    public TestimonialDTO createTestimonial(TestimonialDTO testimonialDTO) {
        Testimonial convertToEntity = TestimonialMapper.convertToEntity(testimonialDTO);
        convertToEntity.setStatus(AppConstants.ACTIVE);
        User user = userDetailsService.findOne(JwtTokenUtils.getLoggedInUserEmail());
        convertToEntity.setCreatedBy(String.valueOf(user.getUserId()));
        convertToEntity.setUpdatedBy(String.valueOf(user.getUserId()));
        convertToEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        convertToEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        testimonialRepository.save(convertToEntity);
        return testimonialDTO;
    }

    @Override
    public TestimonialDTO updateTestimonial(Long testimonialId, TestimonialDTO testimonialDTO) {

        Optional<Testimonial> optionalTestimonial = testimonialRepository.findById(testimonialId);
        if (optionalTestimonial.isPresent()){
            Testimonial testimonial = optionalTestimonial.get();
            testimonial.setName(testimonialDTO.getName());
            testimonial.setRole(testimonialDTO.getRole());
            testimonial.setImage(testimonialDTO.getImage());
            testimonial.setStar(testimonialDTO.getStar());
            testimonial.setMessage(testimonialDTO.getMessage());
            testimonial.setDesignation(testimonialDTO.getDesignation());
            testimonial.setDescription(testimonialDTO.getDescription());

            User user = userDetailsService.findOne(JwtTokenUtils.getLoggedInUserEmail());
            testimonial.setCreatedBy(String.valueOf(user.getUserId()));
            testimonial.setUpdatedBy(String.valueOf(user.getUserId()));
            testimonial.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            testimonial.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

                testimonialRepository.save(testimonial);

                return TestimonialMapper.convertToDTO(testimonial);

        }else {
            return null;
        }
    }

    @Override
    public TestimonialDTO deleteTestimonialById(Long testimonialId) throws ApplicationBusinessException {
        return null;
    }

    @Override
    public TestimonialDTO updateTestimonialStatus(Long testimonialId, String status) {
        return null;
    }
}
