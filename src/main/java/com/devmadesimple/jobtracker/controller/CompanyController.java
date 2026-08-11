package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.request.CompanyRequest;
import com.devmadesimple.jobtracker.dto.response.CompanyResponse;
import com.devmadesimple.jobtracker.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping("/create")
    public ResponseEntity<CompanyResponse> createCompany(@Valid @RequestBody CompanyRequest request) {
        return new ResponseEntity<>(companyService.createCompany(request) ,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getAllCompany(Pageable pageable) {
        return ResponseEntity.ok(companyService.getCompanies(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompanyById(@Valid @RequestBody CompanyRequest request, @PathVariable @Positive Long id) {
        return new ResponseEntity<>(companyService.updateCompany(request, id) ,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable @Positive Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok("Deleted Successfully");
    }

}
