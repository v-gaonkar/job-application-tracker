package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.request.SkillRequest;
import com.devmadesimple.jobtracker.dto.response.SkillResponse;
import com.devmadesimple.jobtracker.service.SkillService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/create")
    public ResponseEntity<SkillResponse> createSkill(@Valid @RequestBody SkillRequest request) {
        return new ResponseEntity<>(skillService.createSkill(request) , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        return ResponseEntity.ok(skillService.getSkills());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<SkillResponse> updateSkillById(@Valid @RequestBody SkillRequest request, @PathVariable @Positive Long id) {
//        return new ResponseEntity<>(skillService.updateSkill(request, id) ,HttpStatus.CREATED);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable @Positive Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok("Deleted Successfully");
    }
}
