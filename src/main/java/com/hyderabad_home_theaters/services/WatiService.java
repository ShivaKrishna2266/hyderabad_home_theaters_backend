package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.WatiParameters;
import com.hyderabad_home_theaters.DTOs.WatiTemplateRequestDTO;
import com.hyderabad_home_theaters.constants.AppConstants;
import com.hyderabad_home_theaters.entity.MessageTemplate;
import com.hyderabad_home_theaters.entity.OtpVerification;
import com.hyderabad_home_theaters.entity.User;
import com.hyderabad_home_theaters.entity.WatiTemplatesResponse;
import com.hyderabad_home_theaters.repository.OtpVerificationRepository;
import com.hyderabad_home_theaters.repository.UserRepository;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WatiService {

    @Value("${wati.templates.url}")
    private String watiTtemplatesUrl;

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

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    /**
     * Get all message templates from WATI API
     */
    public List<MessageTemplate> getTemplateMessages() {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);
        ResponseEntity<WatiTemplatesResponse> exchange = rt.exchange(watiTtemplatesUrl, HttpMethod.GET, entity,
                WatiTemplatesResponse.class);
        WatiTemplatesResponse body = exchange.getBody();
        return body.getMessageTemplates();
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
    public WatiTemplatesResponse sendWatiOTP(String phoneNumber) {
        String otp = generateOTP();
        System.out.println("Generated OTP: " + otp);

        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+91" + phoneNumber;
        }

        System.out.println("Sending OTP to: " + phoneNumber);

        WatiParameters param = new WatiParameters();
        param.setName("otp");
        param.setValue(otp);

        WatiTemplateRequestDTO requestDTO = new WatiTemplateRequestDTO();
        requestDTO.setTemplate_name(watiOtpTemplateName);
        requestDTO.setBroadcast_name(watiOtpTemplateName);
        requestDTO.setParameters(Collections.singletonList(param));


        String apiUrl = "https://live-server-5866.wati.io/api/v1/sendTemplateMessage?whatsappNumber=" + phoneNumber;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<WatiTemplateRequestDTO> request = new HttpEntity<>(requestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<WatiTemplatesResponse> response = restTemplate.postForEntity(
                    apiUrl, request, WatiTemplatesResponse.class);

            System.out.println("Response: " + response.getBody());

            response.getBody().setOtp(otp);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new RuntimeException("Failed to send OTP: " + e.getMessage(), e);
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
