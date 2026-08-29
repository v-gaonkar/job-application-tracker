package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.request.RoundRequest;
import com.devmadesimple.jobtracker.dto.response.RoundResponse;
import com.devmadesimple.jobtracker.service.RoundService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoundController {

    RoundService roundService;

    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @PostMapping("/rounds/create")
    public ResponseEntity<RoundResponse> createRound(@Valid @RequestBody RoundRequest request) {
        return new ResponseEntity<>(roundService.addRound(request), HttpStatus.CREATED);
    }

    @GetMapping("/applications/{applicationId}/rounds")
    public ResponseEntity<List<RoundResponse>> getRoundsForApplication(@PathVariable @Positive Long applicationId) {
        return ResponseEntity.ok(roundService.getRoundsForApplication(applicationId));
    }

    @GetMapping("/rounds")
    public ResponseEntity<List<RoundResponse>> getAllRounds() {
        return ResponseEntity.ok(roundService.getAllRoundsForUser());
    }

    @PutMapping("/rounds/{id}")
    public ResponseEntity<RoundResponse> updateRound(@Valid @RequestBody RoundRequest request, @PathVariable @Positive Long id) {
        return ResponseEntity.ok(roundService.updateRound(request, id));
    }

    @DeleteMapping("/rounds/{id}")
    public ResponseEntity<?> deleteRound(@PathVariable @Positive Long id) {
        roundService.deleteRound(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}