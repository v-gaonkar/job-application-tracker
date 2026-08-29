package com.devmadesimple.jobtracker.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpdateRequest {
    private String username;
    private String email;
    private String targetRole;
    private Integer yearsExperience;
    private String bio;
    private String location;
}