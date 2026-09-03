package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.request.EmailTemplateRequest;
import com.devmadesimple.jobtracker.dto.response.EmailTemplateResponse;
import com.devmadesimple.jobtracker.entity.EmailTemplate;
import com.devmadesimple.jobtracker.exception.EmailTemplateNotFoundException;
import com.devmadesimple.jobtracker.mapper.EmailTemplateMapper;
import com.devmadesimple.jobtracker.repository.EmailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailTemplateService {

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Autowired
    EmailTemplateMapper emailTemplateMapper;

    public EmailTemplateResponse createTemplate(EmailTemplateRequest request) {
        EmailTemplate template = new EmailTemplate();
        template.setName(request.getName());
        template.setSubject(request.getSubject());
        template.setBody(request.getBody());
        template.setCategory(request.getCategory());
        EmailTemplate saved = emailTemplateRepository.save(template);
        return emailTemplateMapper.toResponse(saved);
    }

    public List<EmailTemplateResponse> getAllTemplates() {
        return emailTemplateRepository.findAll().stream()
                .map(emailTemplateMapper::toResponse)
                .toList();
    }

    public EmailTemplateResponse getTemplateById(Long id) {
        EmailTemplate template = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new EmailTemplateNotFoundException("Email template not found with id " + id));
        return emailTemplateMapper.toResponse(template);
    }

    public EmailTemplateResponse updateTemplate(EmailTemplateRequest request, Long id) {
        EmailTemplate template = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new EmailTemplateNotFoundException("Email template not found with id " + id));
        template.setName(request.getName());
        template.setSubject(request.getSubject());
        template.setBody(request.getBody());
        template.setCategory(request.getCategory());
        EmailTemplate saved = emailTemplateRepository.save(template);
        return emailTemplateMapper.toResponse(saved);
    }

    public void deleteTemplate(Long id) {
        EmailTemplate template = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new EmailTemplateNotFoundException("Email template not found with id " + id));
        emailTemplateRepository.delete(template);
    }
}