package com.hyderabad_home_theaters.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class WatiTemplateRequestDTO {

    private String template_name;
    private String broadcast_name;
    private List<WatiParameters> parameters;
}
