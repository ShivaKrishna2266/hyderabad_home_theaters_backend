package com.hyderabad_home_theaters.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyderabad_home_theaters.DTOs.WatiParameters;
import com.hyderabad_home_theaters.DTOs.WatiTemplateRequestDTO;
import com.hyderabad_home_theaters.constants.AppConstants;
import com.hyderabad_home_theaters.entity.MessageTemplate;
import com.hyderabad_home_theaters.entity.User;
import com.hyderabad_home_theaters.entity.WatiTemplatesResponse;
import com.hyderabad_home_theaters.repository.UserRepository;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WatiService {

    @Value("${wati.templates.url}")
    private String watiTemplatesUrl;

    @Value("${wati.token}")
    private String token;

    @Value("${wati.send.template.csv.url}")
    private String sendTemplateCsvUrl;

    @Value("${wati.otp.template.name}")
    private String watiOtpTemplateName;

    @Value("${wati.send.template.msg.url}")
    private String watiTemplateMsgApiUrl;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all message templates from WATI API
     */
    public List<MessageTemplate> getTemplateMessages() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<WatiTemplatesResponse> response = restTemplate.exchange(
                watiTemplatesUrl,
                HttpMethod.GET,
                entity,
                WatiTemplatesResponse.class
        );

        WatiTemplatesResponse responseBody = response.getBody();
        if (responseBody != null) {
            return responseBody.getMessageTemplates();
        }
        return List.of();
    }

    /**
     * Send WATI broadcast SMS to users based on role
     */
    public void sendWatiSMSByRole(String templateName, String role) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        String filePath = "output_users.csv";
        String[] header = {"Name", "CountryCode", "Phone", "AllowBroadcast", "AllowSMS"};

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(header);

            if (AppConstants.USER.equals(role)) {
                List<User> users = userRepository.findAll();
                for (User user : users) {
                    writer.writeNext(new String[]{
                            user.getUsername(),
                            "+91", // Assuming country code is India
                            user.getPhoneNumber(),
                            "TRUE",
                            "TRUE"
                    });
                }
            } else {
                throw new IllegalArgumentException("Invalid role provided: " + role);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file", e);
        }

        File file = new File(filePath);
        body.add(AppConstants.WHATSAPP_NUMBERS_CSV, new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String fullApiUrl = sendTemplateCsvUrl + "?template_name=" + templateName + "&broadcast_name=" + templateName + "_bd";

        ResponseEntity<String> response = restTemplate.postForEntity(fullApiUrl, requestEntity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to send WATI SMS: " + response.getBody());
        }

        // Clean up
        file.delete();
    }

    /**
     * Send OTP using WATI template message API
     */
    public WatiTemplatesResponse sendWatiOTP(String phno) {
        RestTemplate rt = new RestTemplate();
        String apiUrl = watiTemplateMsgApiUrl + "?whatsappNumber=" + phno;


        String otp = generateOTP();

        WatiParameters params = new WatiParameters();
        params.setName("otp");
        params.setValue(otp);

        WatiTemplateRequestDTO requestDTO = new WatiTemplateRequestDTO();
        requestDTO.setTemplate_name(watiOtpTemplateName);
        requestDTO.setBroadcast_name(watiOtpTemplateName);
        requestDTO.setParameters(Arrays.asList(params));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WatiTemplateRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<WatiTemplatesResponse> response = rt.postForEntity(apiUrl, requestEntity, WatiTemplatesResponse.class);

        if (response.getBody() != null) {
            response.getBody().setOtp(otp);
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to send OTP via WATI");
        }
    }




    private String generateOTP() {
        // Length of the OTP
        int length = 4;
        // Generate random digits
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(ThreadLocalRandom.current().nextInt(0, 10));
        }
        return otp.toString();
    }
}
