package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.request.LoginRequest;
import com.devmadesimple.jobtracker.dto.response.LoginResponse;
import com.devmadesimple.jobtracker.entity.EmailTemplate;
import com.devmadesimple.jobtracker.entity.PasswordResetToken;
import com.devmadesimple.jobtracker.entity.RefreshToken;
import com.devmadesimple.jobtracker.entity.User;
import com.devmadesimple.jobtracker.exception.EmailTemplateNotFoundException;
import com.devmadesimple.jobtracker.repository.EmailTemplateRepository;
import com.devmadesimple.jobtracker.repository.PasswordResetTokenRepository;
import com.devmadesimple.jobtracker.repository.RefreshTokenRepository;
import com.devmadesimple.jobtracker.repository.UserRepository;
import com.devmadesimple.jobtracker.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    public LoginResponse refresh(String refreshTokenValue) {
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (storedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(storedToken);
            throw new RuntimeException("Refresh token expired");
        }

        String newAccessToken = jwtUtil.generateToken(storedToken.getUser().getEmail());

        LoginResponse response = new LoginResponse();
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(refreshTokenValue);
        return response;
    }

    @Transactional
    public void logout(String refreshTokenValue) {
        if (refreshTokenValue == null || refreshTokenValue.isBlank()) {
            return;
        }
        refreshTokenRepository.deleteByToken(refreshTokenValue);
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail());

        String accessToken = jwtUtil.generateToken(request.getEmail());
        String refreshTokenValue = jwtUtil.generateRefreshToken();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshToken);

        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshTokenValue);
        return response;
    }

    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return;
        }

        passwordResetTokenRepository.deleteByUser(user);

        String token = jwtUtil.generateRefreshToken();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(30));
        passwordResetTokenRepository.save(resetToken);

        sendPasswordResetEmail(user, token);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset link"));

        if (resetToken.isUsed() || resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired reset link");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        refreshTokenRepository.deleteAllByUser(user);
    }

    private void sendPasswordResetEmail(User user, String token) {
        EmailTemplate template = emailTemplateRepository.findByCategoryAndName("auth", "Password Reset")
                .orElseThrow(() -> new EmailTemplateNotFoundException("Missing system template: Password Reset"));

        String resetLink = frontendUrl + "/reset-password?token=" + token;

        String subject = template.getSubject()
                .replace("{{userName}}", user.getUsername());
        String body = template.getBody()
                .replace("{{userName}}", user.getUsername())
                .replace("{{resetLink}}", resetLink);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }
}