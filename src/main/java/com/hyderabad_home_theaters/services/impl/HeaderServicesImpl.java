package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.HeaderDTO;
import com.hyderabad_home_theaters.entity.Header;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;
import com.hyderabad_home_theaters.exception.ResourceNotFoundException;
import com.hyderabad_home_theaters.mapper.HeaderMapper;
import com.hyderabad_home_theaters.repository.HeaderRepository;
import com.hyderabad_home_theaters.services.HeaderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HeaderServicesImpl implements HeaderServices {


    @Autowired
    private HeaderRepository headerRepository;
    @Override
    public List<HeaderDTO> getAllHeaders() {
        List<Header> headers = headerRepository.findAll();
        return  headers
                .stream()
                .map(HeaderMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HeaderDTO getHeaderById(Long headerId) {
        return null;
    }

    @Override
    public HeaderDTO createHeader(HeaderDTO headerDTO) {
        try {
            Header headerEntity = new Header();

            headerEntity.setText(headerDTO.getText());
            headerEntity.setPhoneNumber(headerDTO.getPhoneNumber());
            headerEntity.setCreatedBy("System");
            headerEntity.setModifiedBy("System");
            headerEntity.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            headerEntity.setModifiedDate(Timestamp.valueOf(LocalDateTime.now()));


            Header savedheader = headerRepository.save(headerEntity);
            HeaderDTO headerDTO1 = HeaderMapper.convertToDTO(savedheader);
            return headerDTO1;
        } catch (Exception e) {
            throw new ApplicationBusinessException("Error occurred while creating Header.");
        }
    }

    @Override
    public HeaderDTO updateHeader(Long headerId, HeaderDTO headerDTO) {
        Optional<Header> optionalHeader = headerRepository.findById(headerId);
        if (optionalHeader.isPresent()) {
            Header header = optionalHeader.get();
            header.setText(headerDTO.getText());
            header.setPhoneNumber(headerDTO.getPhoneNumber());

            header.setCreatedBy("System");
            header.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            header.setModifiedBy("System");
            header.setModifiedDate(Timestamp.valueOf(LocalDateTime.now()));

            Header savedHeader = headerRepository.save(header);
            HeaderDTO headerDTO1 =  HeaderMapper.convertToDTO(savedHeader);
            return headerDTO1;
        }
        throw new RuntimeException("Header is not found");
    }

    @Override
    public void deleteHeaderById(Long headerId) {
        if (!headerRepository.existsById(headerId)) {
            throw new ResourceNotFoundException("Header with ID " + headerId + " not found");
        }
        headerRepository.deleteById(headerId);
    }
}
