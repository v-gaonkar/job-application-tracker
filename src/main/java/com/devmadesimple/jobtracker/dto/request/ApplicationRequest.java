package com.devmadesimple.jobtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationRequest {
    @NotBlank
    private String jobTitle;

    private String jobDescription;

    private Long companyId;

    private String companyName;

    @NotNull
    private LocalDateTime dateApplied;

    private String source;

    private Integer minSalary;

    private Integer maxSalary;

    private String currency;

    private String location;

    private String status;
}
