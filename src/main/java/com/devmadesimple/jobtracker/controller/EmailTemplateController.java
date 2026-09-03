package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.request.EmailTemplateRequest;
import com.devmadesimple.jobtracker.dto.response.EmailTemplateResponse;
import com.devmadesimple.jobtracker.service.EmailTemplateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email-templates")
public class EmailTemplateController {

    EmailTemplateService emailTemplateService;

    public EmailTemplateController(EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

    @PostMapping("/create")
    public ResponseEntity<EmailTemplateResponse> createTemplate(@Valid @RequestBody EmailTemplateRequest request) {
        return new ResponseEntity<>(emailTemplateService.createTemplate(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmailTemplateResponse>> getAllTemplates() {
        return ResponseEntity.ok(emailTemplateService.getAllTemplates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailTemplateResponse> getTemplateById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(emailTemplateService.getTemplateById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailTemplateResponse> updateTemplate(@Valid @RequestBody EmailTemplateRequest request, @PathVariable @Positive Long id) {
        return ResponseEntity.ok(emailTemplateService.updateTemplate(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTemplate(@PathVariable @Positive Long id) {
        emailTemplateService.deleteTemplate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}