package com.devmadesimple.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationResponse {

    private Long id;

    private String jobTitle;

    private String jobDescription;

    private Long companyId;

    private String companyName;

    private String companyWebsite;

    private String companyCareerPageUrl;

    private LocalDateTime dateApplied;

    private String source;

    private Integer minSalary;

    private Integer maxSalary;

    private String currency;

    private String location;

    private String status;

    private LocalDateTime createdAt;

}
