package com.hyderabad_home_theaters.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyderabad_home_theaters.DTOs.LoginDTO;
import com.hyderabad_home_theaters.DTOs.ProfileDTO;
import com.hyderabad_home_theaters.DTOs.UserDTO;
import com.hyderabad_home_theaters.config.CustomUserDetailsService;
import com.hyderabad_home_theaters.config.JwtUtil;
import com.hyderabad_home_theaters.entity.Role;
import com.hyderabad_home_theaters.entity.User;
import com.hyderabad_home_theaters.services.UserDetailsService;
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

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserDTO request) {
        userDetailsService.registerUser(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");

        return ResponseEntity.ok(response);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO request) {
        try {
            // Perform authentication and capture the Authentication object
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Generate JWT using the Authentication object
            String jwt = jwtUtil.generateToken(authentication);

            // Load user details and entity
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
            User user = userDetailsService.findByUsername(request.getUsername());

            // Get profile
            ProfileDTO profileDTO = userDetailsService.getProfileUsername(user.getUsername());

            // Convert profileDTO to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String profileJson = objectMapper.writeValueAsString(profileDTO);

            // Prepare JSON response
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("userId", user.getUserId());
            jsonResponse.put("role", user.getRoles().stream().findFirst().get().getRoleName());
            jsonResponse.put("username", user.getUsername());
            jsonResponse.put("token", jwt);
            jsonResponse.put("profile", profileJson != null ? profileJson : "");

            // Add Authorization header
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

            return new ResponseEntity<>(jsonResponse.toString(), headers, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }
    }
