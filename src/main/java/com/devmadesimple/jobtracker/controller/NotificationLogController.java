package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.response.NotificationLogResponse;
import com.devmadesimple.jobtracker.service.NotificationLogService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications/{applicationId}/notifications")
public class NotificationLogController {

    NotificationLogService notificationLogService;

    public NotificationLogController(NotificationLogService notificationLogService) {
        this.notificationLogService = notificationLogService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationLogResponse>> getNotifications(@PathVariable @Positive Long applicationId) {
        return ResponseEntity.ok(notificationLogService.getNotificationsForApplication(applicationId));
    }
}