package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.TestimonialDTO;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;

import java.util.List;

public interface TestimonialService {

    TestimonialDTO getTestimonialById(Long testimonialId);

    List<TestimonialDTO> getAllTestimonials();

    TestimonialDTO createTestimonial(TestimonialDTO testimonialDTO);

    TestimonialDTO updateTestimonial(Long testimonialId, TestimonialDTO testimonialDTO);

    TestimonialDTO deleteTestimonialById(Long testimonialId) throws ApplicationBusinessException;

    TestimonialDTO updateTestimonialStatus (Long testimonialId, String status  );
}
