package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.ContactUsDTO;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;

import java.util.List;

public interface ContactUsService {

    List<ContactUsDTO> getAllContactUs();
    void deleteContactUs(Long contactUsId);

    ContactUsDTO createContactUs(ContactUsDTO contactUsDTO) throws ApplicationBusinessException;

    ContactUsDTO updateContactUs(ContactUsDTO contactUsDTO);
    ContactUsDTO updateContactEnquiryStatusAndComments(Long id, String leadStatus, String comment);
}
