package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.entity.User;
import com.hyderabad_home_theaters.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;


    @Value("${spring.reset.url}")
    private String resetBaseUrl;

    public void sendResetEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        Timestamp expiryTime = Timestamp.from(Instant.now().plus(Duration.ofHours(1)));

        user.setResetToken(token);
        user.setTokenExpiry(expiryTime);
        userRepository.save(user);

        // Build the reset link dynamically: include email and token as params
        String resetLink = resetBaseUrl + user.getEmail() + "&token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password Reset Request");
        message.setText("Hi " + user.getUsername() + ",\n\n"
                + "Click the link below to reset your password:\n"
                + resetLink + "\n\n"
                + "If you didn’t request this, please ignore this email.");

        mailSender.send(message);
    }


    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (user.getTokenExpiry().before(Timestamp.from(Instant.now()))) {
            throw new RuntimeException("Token expired");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(newPassword));

        user.setResetToken(null);
        user.setTokenExpiry(null);

        userRepository.save(user);
    }

}
