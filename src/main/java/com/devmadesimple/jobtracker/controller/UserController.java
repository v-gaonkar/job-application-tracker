package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.request.RegisterRequest;
import com.devmadesimple.jobtracker.dto.request.UserProfileUpdateRequest;
import com.devmadesimple.jobtracker.dto.response.UserProfileResponse;
import com.devmadesimple.jobtracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return new ResponseEntity<>("Registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile() {
        return ResponseEntity.ok(userService.getProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(@RequestBody UserProfileUpdateRequest request) {
        return ResponseEntity.ok(userService.updateProfile(request));
    }

}
