package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.TestimonialDTO;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;

import java.util.List;

public interface TestimonialService {

    TestimonialDTO getTestimonialById(Long testimonialId);

    List<TestimonialDTO> getAllTestimonials();

    TestimonialDTO createTestimonial(TestimonialDTO testimonialDTO);

    TestimonialDTO updateTestimonial(Long testimonialId, TestimonialDTO testimonialDTO);

    void deleteTestimonialById(Long testimonialId);

    TestimonialDTO updateTestimonialStatus (Long testimonialId, String status  );
}
