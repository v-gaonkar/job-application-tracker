package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.response.SkillResponse;
import com.devmadesimple.jobtracker.service.ApplicationSkillService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application/{applicationId}/skills")
public class ApplicationSkillController {

    @Autowired
    ApplicationSkillService applicationSkillService;

    @PostMapping("/{skillId}")
    public ResponseEntity<?> addSkill(@PathVariable @Positive Long applicationId, @PathVariable @Positive Long skillId) {
        applicationSkillService.addSkillToApplication(applicationId, skillId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<?> removeSkill(@PathVariable @Positive Long applicationId, @PathVariable @Positive Long skillId) {
        applicationSkillService.removeSkillFromApplication(applicationId, skillId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getApplicationSkills(@PathVariable @Positive Long applicationId) {
        return ResponseEntity.ok(applicationSkillService.getSkillsForApplication(applicationId));
    }
}