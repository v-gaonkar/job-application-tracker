package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.request.RegisterRequest;
import com.devmadesimple.jobtracker.dto.request.UserProfileUpdateRequest;
import com.devmadesimple.jobtracker.dto.response.UserProfileResponse;
import com.devmadesimple.jobtracker.entity.User;
import com.devmadesimple.jobtracker.exception.EmailAlreadyExistsException;
import com.devmadesimple.jobtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CurrentUserProvider currentUserProvider;

    public void registerUser(RegisterRequest request) {
        if(userRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        } else {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(User.Role.USER);
            userRepo.save(user);
        }
    }

    public UserProfileResponse getProfile() {
        User user = currentUserProvider.getCurrentUser();
        UserProfileResponse response = new UserProfileResponse();
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setTargetRole(user.getTargetRole());
        response.setYearsExperience(user.getYearsExperience());
        response.setBio(user.getBio());
        response.setLocation(user.getLocation());
        return response;
    }

    public UserProfileResponse updateProfile(UserProfileUpdateRequest request) {
        User user = currentUserProvider.getCurrentUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setTargetRole(request.getTargetRole());
        user.setYearsExperience(request.getYearsExperience());
        user.setBio(request.getBio());
        user.setLocation(request.getLocation());
        User saved = userRepo.save(user);

        UserProfileResponse response = new UserProfileResponse();
        response.setUsername(saved.getUsername());
        response.setEmail(saved.getEmail());
        response.setTargetRole(saved.getTargetRole());
        response.setYearsExperience(saved.getYearsExperience());
        response.setBio(saved.getBio());
        response.setLocation(saved.getLocation());
        return response;
    }
}
