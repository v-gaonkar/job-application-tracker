package com.devmadesimple.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {
    private String username;
    private String email;
    private String targetRole;
    private Integer yearsExperience;
    private String bio;
    private String location;
}