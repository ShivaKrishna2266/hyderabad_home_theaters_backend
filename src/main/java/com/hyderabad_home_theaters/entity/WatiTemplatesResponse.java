package com.hyderabad_home_theaters.entity;

import com.hyderabad_home_theaters.DTOs.WatiParameters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatiTemplatesResponse {

    private String result;
    private String phone_number;
    private List<WatiParameters> parameters;
    private boolean validWhatsAppNumber;
    private List<MessageTemplate> messageTemplates;
    private String otp;

}
