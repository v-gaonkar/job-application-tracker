package com.devmadesimple.jobtracker.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {
    @NotBlank
    private String name;

    private String website;

    private String industry;

    private String notes;

    private String careerPageUrl;
}
