package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.response.SkillResponse;
import com.devmadesimple.jobtracker.entity.*;
import com.devmadesimple.jobtracker.exception.ApplicationNotFoundException;
import com.devmadesimple.jobtracker.exception.SkillNotFoundException;
import com.devmadesimple.jobtracker.mapper.SkillMapper;
import com.devmadesimple.jobtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationSkillService {
    @Autowired
    ApplicationSkillRepository applicationSkillRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillMapper skillMapper;

    @Autowired
    CurrentUserProvider currentUserProvider;


    private Application getOwnedApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id " + applicationId));
        if (!application.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new ApplicationNotFoundException("Application not found with id " + applicationId);
        }
        return application;
    }

    public void addSkillToApplication(Long applicationId, Long skillId) {
        Application application = getOwnedApplication(applicationId);
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNotFoundException("Skill not found with id " + skillId));

        if (applicationSkillRepository.existsByApplication_IdAndSkill_Id(applicationId, skillId)) {
            return;
        }

        ApplicationSkill applicationSkill = new ApplicationSkill();
        applicationSkill.setApplication(application);
        applicationSkill.setSkill(skill);
        applicationSkillRepository.save(applicationSkill);
    }

    public void removeSkillFromApplication(Long applicationId, Long skillId) {
        getOwnedApplication(applicationId);
        applicationSkillRepository.deleteByApplication_IdAndSkill_Id(applicationId, skillId);
    }

    public List<SkillResponse> getSkillsForApplication(Long applicationId) {
        getOwnedApplication(applicationId);
        return applicationSkillRepository.findByApplication_Id(applicationId).stream()
                .map(as -> skillMapper.toResponse(as.getSkill()))
                .collect(Collectors.toList());
    }
}