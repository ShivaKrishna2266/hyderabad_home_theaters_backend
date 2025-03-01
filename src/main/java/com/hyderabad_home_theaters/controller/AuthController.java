package com.hyderabad_home_theaters.controller;

import com.hyderabad_home_theaters.DTOs.LoginDTO;
import com.hyderabad_home_theaters.DTOs.UserDTO;
import com.hyderabad_home_theaters.config.CustomUserDetailsService;
import com.hyderabad_home_theaters.config.JwtUtil;
import com.hyderabad_home_theaters.entity.Role;
import com.hyderabad_home_theaters.entity.User;
import com.hyderabad_home_theaters.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200") // ✅ Allow frontend calls
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
    public ResponseEntity<String> registerUser(@RequestBody UserDTO request) {
        userDetailsService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO request) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        if(userDetails == null){
            String errorMessage = "Active user is not found";
            return ResponseEntity.badRequest().body(errorMessage);
        }
        final Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String jwt = jwtUtil.generateToken(authentication);
        Map<String, String> response = new HashMap<>();
        User user = userDetailsService.findByUserName(request.getUsername());
        Role role = user.getRoles().stream().findFirst().get();
        response.put("token", jwt);
        response.put("username", user.getUsername());
        response.put("role",role.getRoleName());
        return ResponseEntity.ok(response);
    }


}
