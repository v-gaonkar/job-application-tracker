package com.devmadesimple.jobtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequest {
    @NotNull
    private Long companyId;

    @NotBlank
    private String name;

    private String email;

    private String role;

    private String phoneNo;

    private String notes;
}