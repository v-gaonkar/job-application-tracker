package com.devmadesimple.jobtracker.controller;

import com.devmadesimple.jobtracker.dto.request.ContactRequest;
import com.devmadesimple.jobtracker.dto.response.ContactResponse;
import com.devmadesimple.jobtracker.service.ContactService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContactController {

    ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/contacts/create")
    public ResponseEntity<ContactResponse> createContact(@Valid @RequestBody ContactRequest request) {
        return new ResponseEntity<>(contactService.createContact(request), HttpStatus.CREATED);
    }

    @GetMapping("/companies/{companyId}/contacts")
    public ResponseEntity<Page<ContactResponse>> getContactsForCompany(
            @PathVariable @Positive Long companyId, Pageable pageable) {
        return ResponseEntity.ok(contactService.getContactsForCompany(companyId, pageable));
    }

    @GetMapping("/companies/{companyId}/contacts/count")
    public ResponseEntity<Long> getContactCount(@PathVariable @Positive Long companyId) {
        return ResponseEntity.ok(contactService.countContactsForCompany(companyId));
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ContactResponse> getContactById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<ContactResponse> updateContact(@Valid @RequestBody ContactRequest request, @PathVariable @Positive Long id) {
        return ResponseEntity.ok(contactService.updateContact(request, id));
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable @Positive Long id) {
        contactService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}