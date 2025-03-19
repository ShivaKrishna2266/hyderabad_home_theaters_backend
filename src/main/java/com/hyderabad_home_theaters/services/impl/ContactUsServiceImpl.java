package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.ContactUsDTO;
import com.hyderabad_home_theaters.entity.ContactUs;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;
import com.hyderabad_home_theaters.mapper.ContactUsMapper;
import com.hyderabad_home_theaters.repository.ContactUsRepository;
import com.hyderabad_home_theaters.services.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContactUsServiceImpl implements ContactUsService {


    @Autowired
    private ContactUsRepository contactUsRepository;
    @Override
    public List<ContactUsDTO> getAllContactUs() {
        List<ContactUsDTO> contactUsDTOList = new ArrayList<>();
        List<ContactUs> contactUsList = contactUsRepository.findAll();
        for (ContactUs contactUs : contactUsList) {
            ContactUsDTO contactUsDTO = new ContactUsDTO();
            contactUsDTO.setId(contactUs.getId());
            contactUsDTO.setName(contactUs.getName());
            contactUsDTO.setEmailId(contactUs.getEmailId());
            contactUsDTO.setPhoneNo(contactUs.getPhoneNo());
            contactUsDTO.setCountryCode(contactUs.getCountryCode());
            contactUsDTO.setMessage(contactUs.getMessage());
            contactUsDTO.setComment(contactUs.getComment());
            contactUsDTO.setCreatedAt(contactUs.getCreatedAt());
            contactUsDTO.setUpdatedAt(contactUs.getUpdatedAt());
            contactUsDTO.setLeadStatus(contactUs.getLeadStatus());
            contactUsDTOList.add(contactUsDTO);
        }
        return contactUsDTOList;
    }

    @Override
    public void deleteContactUs(Long contactUsId) {

    }

    @Override
    public ContactUsDTO createContactUs(ContactUsDTO contactUsDTO) throws ApplicationBusinessException {
        try {
            ContactUs contactUsEntity = new ContactUs();
            contactUsEntity.setCreatedBy("System");
            contactUsEntity.setUpdatedBy("System");
            contactUsEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            contactUsEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            contactUsEntity.setName(contactUsDTO.getName());
            contactUsEntity.setEmailId(contactUsDTO.getEmailId());
            contactUsEntity.setCountryCode(contactUsDTO.getCountryCode());
            contactUsEntity.setPhoneNo(contactUsDTO.getPhoneNo());
            contactUsEntity.setMessage(contactUsDTO.getMessage());
            contactUsEntity.setLeadStatus(contactUsDTO.getLeadStatus());
            contactUsEntity = contactUsRepository.save(contactUsEntity);
            ContactUsDTO savedDTO = contactUsDTO;
            savedDTO.setId(contactUsEntity.getId());
            return savedDTO;
        } catch (Exception e) {
            throw new ApplicationBusinessException("Error occurred while creating contact.");
        }
    }

    @Override
    public ContactUsDTO updateContactUs(ContactUsDTO contactUsDTO) {
        return null;
    }

    @Override
    public ContactUsDTO updateContactEnquiryStatusAndComments(Long id, String leadStatus, String comment) {
        Optional<ContactUs> contactUsOptional = contactUsRepository.findById(id);
        if (contactUsOptional.isPresent()) {
            ContactUs contactUs = contactUsOptional.get();
            contactUs.setLeadStatus(leadStatus);
            contactUs.setComment(comment);
            ContactUs updatedContactUs = contactUsRepository.save(contactUs);
            return ContactUsMapper.convertToDTO(updatedContactUs);
        }
        return null;
    }
}
