package com.hyderabad_home_theaters.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyderabad_home_theaters.DTOs.LoginDTO;
import com.hyderabad_home_theaters.DTOs.OtpVerificationDTO;
import com.hyderabad_home_theaters.DTOs.ProfileDTO;
import com.hyderabad_home_theaters.DTOs.UserDTO;
import com.hyderabad_home_theaters.DTOs.WatiTemplateRequestDTO;
import com.hyderabad_home_theaters.config.CustomUserDetailsService;
import com.hyderabad_home_theaters.config.JwtUtil;
import com.hyderabad_home_theaters.entity.Role;
import com.hyderabad_home_theaters.entity.User;
import com.hyderabad_home_theaters.entity.WatiTemplatesResponse;
import com.hyderabad_home_theaters.services.UserDetailsService;
import com.hyderabad_home_theaters.services.UserProfileService;
import com.hyderabad_home_theaters.services.WatiService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserProfileService userProfileService;


    @Autowired
    private WatiService watiService;

    private Map<String, String> otpStore = new ConcurrentHashMap<>();

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserDTO request) {
        Map<String, String> response = new HashMap<>();
        try {
            // Register user and send OTP
            userDetailsService.registerUser(request);
            response.put("message", "OTP sent. Please verify your OTP to complete registration.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String jwt = jwtUtil.generateToken(authentication);

//            UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
            User user = userDetailsService.findByUsername(request.getUsername());
//            ProfileDTO profileDTO = userDetailsService.getProfileUsername(user.getUsername());
            ProfileDTO profileDTO = userProfileService.getUserByFirstName(user.getUsername());


            ObjectMapper objectMapper = new ObjectMapper();
            String profileJson = objectMapper.writeValueAsString(profileDTO);

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("userId", user.getUserId());
            jsonResponse.put("role", user.getRoles().stream().findFirst().get().getRoleName());
            jsonResponse.put("username", user.getUsername());
            jsonResponse.put("token", jwt);
            jsonResponse.put("profile",profileJson!=null?profileJson:"" );

//            jsonResponse.put("profile", profileDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

            return new ResponseEntity<>(jsonResponse.toString(), headers, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }



    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, String>> sendOtp(@RequestParam String phoneNumber) {
        WatiTemplatesResponse response = watiService.sendWatiOTP(phoneNumber);
        otpStore.put(phoneNumber, response.getOtp()); // Store OTP temporarily

        Map<String, String> result = new HashMap<>();
        result.put("message", "OTP sent successfully");

        return ResponseEntity.ok(result);
    }

//    @PostMapping("/verify-otp")
//    public ResponseEntity<String> verifyOtpAndRegister(@RequestBody UserDTO request) {
//        try {
//            // Verify OTP and complete registration
//            userDetailsService.verifyOtpAndRegisterUser(request);
//            return ResponseEntity.ok("Registration successful!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }


    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtpAndRegister(@RequestBody UserDTO request) {
        Map<String, String> response = new HashMap<>();

        try {
            userDetailsService.verifyOtpAndRegisterUser(request);
            response.put("message", "Registration successful!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "OTP verification failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    }
