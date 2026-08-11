package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.request.ApplicationRequest;
import com.devmadesimple.jobtracker.dto.request.StatusUpdateRequest;
import com.devmadesimple.jobtracker.dto.response.ApplicationResponse;
import com.devmadesimple.jobtracker.service.ApplicationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applicationApi/applications")
public class ApplicationController {

    ApplicationService applicationService;
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApplicationResponse> createApplication(@Valid @RequestBody ApplicationRequest request) {
        return new ResponseEntity<>(applicationService.createApplication(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ApplicationResponse>> getAllApplications(Pageable pageable) {
        return ResponseEntity.ok(applicationService.getApplications(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplicationById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> updateApplication(@Valid @RequestBody ApplicationRequest request, @PathVariable @Positive Long id) {
        return ResponseEntity.ok(applicationService.updateApplication(request, id));
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<ApplicationResponse> patchApplication(@Valid @RequestBody StatusUpdateRequest request, @PathVariable @Positive Long id) {
        return ResponseEntity.ok(applicationService.patchApplication(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable @Positive Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Application deleted successfully");
    }

}
