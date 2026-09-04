package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.response.AIMatchResponse;
import com.devmadesimple.jobtracker.service.AIMatchService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
public class AIMatchController {
    @Autowired
    AIMatchService aiMatchService;

    @PostMapping("/{id}/ai-match")
    public ResponseEntity<AIMatchResponse> generateMatch(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(aiMatchService.generateMatch(id));
    }
}
