package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.response.SkillResponse;
import com.devmadesimple.jobtracker.service.UserSkillService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/skills")
public class UserSkillController {

    @Autowired
    UserSkillService userSkillService;

    @PostMapping("/{skillId}")
    public ResponseEntity<?> addSkill(@PathVariable @Positive Long userId, @PathVariable @Positive Long skillId) {
        userSkillService.addSkillToUser(skillId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<?> removeSkill(@PathVariable @Positive Long userId, @PathVariable @Positive Long skillId) {
        userSkillService.removeSkillFromUser(skillId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getUserSkills(@PathVariable @Positive Long userId) {
        return ResponseEntity.ok(userSkillService.getSkillsForUser());
    }
}