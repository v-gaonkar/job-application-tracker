package com.devmadesimple.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationLogResponse {
    private Long id;
    private String type;
    private Long roundId;
    private LocalDateTime sentAt;
}