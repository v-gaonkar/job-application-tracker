package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.response.StatusHistoryResponse;
import com.devmadesimple.jobtracker.service.StatusHistoryService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications/{applicationId}/status-history")
public class StatusHistoryController {

    StatusHistoryService statusHistoryService;

    public StatusHistoryController(StatusHistoryService statusHistoryService) {
        this.statusHistoryService = statusHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<StatusHistoryResponse>> getHistory(@PathVariable @Positive Long applicationId) {
        return ResponseEntity.ok(statusHistoryService.getHistoryForApplication(applicationId));
    }
}