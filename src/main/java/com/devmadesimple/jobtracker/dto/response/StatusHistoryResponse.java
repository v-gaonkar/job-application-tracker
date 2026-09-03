package com.devmadesimple.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StatusHistoryResponse {
    private Long id;
    private String oldStatus;
    private String newStatus;
    private LocalDateTime changedAt;
}