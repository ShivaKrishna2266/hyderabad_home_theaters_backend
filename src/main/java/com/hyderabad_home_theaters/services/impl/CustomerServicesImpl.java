package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.CustomerDTO;
import com.hyderabad_home_theaters.entity.Category;
import com.hyderabad_home_theaters.entity.Customer;
import com.hyderabad_home_theaters.mapper.CategoryMapper;
import com.hyderabad_home_theaters.mapper.CustomerMapper;
import com.hyderabad_home_theaters.repository.CustomerRepository;
import com.hyderabad_home_theaters.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServicesImpl implements CustomerServices {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
