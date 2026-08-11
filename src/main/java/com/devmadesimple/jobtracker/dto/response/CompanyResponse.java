package com.devmadesimple.jobtracker.dto.response;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CompanyResponse {
    private Long id;

    private String name;

    private String website;

    private String industry;

    private String notes;

    private String careerPageUrl;

    private LocalDateTime createdAt;
}
