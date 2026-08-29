package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.request.ContactRequest;
import com.devmadesimple.jobtracker.dto.response.ContactResponse;
import com.devmadesimple.jobtracker.entity.Company;
import com.devmadesimple.jobtracker.entity.Contact;
import com.devmadesimple.jobtracker.exception.CompanyNotFoundException;
import com.devmadesimple.jobtracker.exception.ContactNotFoundException;
import com.devmadesimple.jobtracker.exception.DuplicateContactException;
import com.devmadesimple.jobtracker.mapper.ContactMapper;
import com.devmadesimple.jobtracker.repository.CompanyRepository;
import com.devmadesimple.jobtracker.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ContactMapper contactMapper;

    @Autowired
    CurrentUserProvider currentUserProvider;

    private Company getOwnedCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id " + companyId));
        if (!company.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new CompanyNotFoundException("Company not found with id " + companyId);
        }
        return company;
    }

    private Contact getOwnedContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id " + id));
        if (!contact.getCompany().getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new ContactNotFoundException("Contact not found with id " + id);
        }
        return contact;
    }

    public ContactResponse createContact(ContactRequest request) {
        Company company = getOwnedCompany(request.getCompanyId());

        Contact contact = new Contact();
        contact.setCompany(company);
        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setRole(request.getRole());
        contact.setPhoneNo(request.getPhoneNo());
        contact.setNotes(request.getNotes());

        try {
            Contact saved = contactRepository.save(contact);
            return contactMapper.toResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateContactException("A contact with this email already exists for this company");
        }
    }

    public Page<ContactResponse> getContactsForCompany(Long companyId, Pageable pageable) {
        getOwnedCompany(companyId);
        return contactRepository.findByCompany_Id(companyId, pageable)
                .map(contactMapper::toResponse);
    }

    public ContactResponse getContactById(Long id) {
        Contact contact = getOwnedContact(id);
        return contactMapper.toResponse(contact);
    }

    public ContactResponse updateContact(ContactRequest request, Long id) {
        Contact contact = getOwnedContact(id);
        Company company = getOwnedCompany(request.getCompanyId());

        contact.setCompany(company);
        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setRole(request.getRole());
        contact.setPhoneNo(request.getPhoneNo());
        contact.setNotes(request.getNotes());

        try {
            Contact saved = contactRepository.save(contact);
            return contactMapper.toResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateContactException("A contact with this email already exists for this company");
        }
    }

    public void deleteContact(Long id) {
        Contact contact = getOwnedContact(id);
        contactRepository.delete(contact);
    }

    public long countContactsForCompany(Long companyId) {
        getOwnedCompany(companyId);
        return contactRepository.countByCompany_Id(companyId);
    }
}