package com.devmadesimple.jobtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoundRequest {

    @NotNull
    private Long applicationId;

    @NotBlank
    private String round;

    private LocalDateTime interviewDate;

    private String status;

    private String notes;
}
