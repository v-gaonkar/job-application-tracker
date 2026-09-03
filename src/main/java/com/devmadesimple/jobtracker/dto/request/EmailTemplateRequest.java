package com.devmadesimple.jobtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailTemplateRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String subject;

    @NotBlank
    private String body;

    @NotBlank
    private String category;
}