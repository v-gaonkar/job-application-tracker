package com.devmadesimple.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContactResponse {
    private Long id;
    private Long companyId;
    private String companyName;
    private String name;
    private String email;
    private String role;
    private String phoneNo;
    private String notes;
    private LocalDateTime createdAt;
}