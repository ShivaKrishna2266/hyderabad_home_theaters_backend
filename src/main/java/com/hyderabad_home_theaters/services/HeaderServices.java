package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.HeaderDTO;

import java.util.List;

public interface HeaderServices {

    List<HeaderDTO> getAllHeaders();

    HeaderDTO getHeaderById(Long headerId);

    HeaderDTO createHeader(HeaderDTO headerDTO);

    HeaderDTO updateHeader(Long headerId, HeaderDTO headerDTO);

    void deleteHeaderById(Long headerId);
}
