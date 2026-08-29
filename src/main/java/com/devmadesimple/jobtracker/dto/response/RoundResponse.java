package com.devmadesimple.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoundResponse {
    private Long id;

    private Long applicationId;

    private String applicationJobTitle;

    private String companyName;

    private String round;

    private LocalDateTime interviewDate;

    private String status;

    private String notes;
}