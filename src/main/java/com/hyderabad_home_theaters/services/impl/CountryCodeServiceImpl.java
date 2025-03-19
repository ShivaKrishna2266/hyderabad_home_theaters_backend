package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.CountryCodeDTO;
import com.hyderabad_home_theaters.entity.CountryCode;
import com.hyderabad_home_theaters.repository.CountryCodeRepository;
import com.hyderabad_home_theaters.services.CountryCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryCodeServiceImpl implements CountryCodeService {

    @Autowired
    private CountryCodeRepository countryCodeRepository;
    @Override
    public List<CountryCodeDTO> getAllCountryCodes() {
        List<CountryCodeDTO> countryCodeDTOS = new ArrayList<>();
        List<CountryCode> countryCodeList = countryCodeRepository.findAll();
        for(CountryCode countryCode: countryCodeList) {
            CountryCodeDTO countryCodeDTO = new CountryCodeDTO();
            countryCodeDTO.setId(countryCode.getId());
            countryCodeDTO.setCountryCode(countryCode.getCountryCode());
            countryCodeDTO.setCountryFlag(countryCode.getCountryFlag());
            countryCodeDTO.setCountryName(countryCode.getCountryName());
            countryCodeDTO.setStatus(countryCode.getStatus());
            countryCodeDTOS.add(countryCodeDTO);
        }
        return countryCodeDTOS;
    }
}
