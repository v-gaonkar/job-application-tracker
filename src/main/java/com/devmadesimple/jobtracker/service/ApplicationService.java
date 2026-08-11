package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.request.ApplicationRequest;
import com.devmadesimple.jobtracker.dto.request.StatusUpdateRequest;
import com.devmadesimple.jobtracker.dto.response.ApplicationResponse;
import com.devmadesimple.jobtracker.entity.Application;
import com.devmadesimple.jobtracker.entity.Company;
import com.devmadesimple.jobtracker.entity.StatusHistory;
import com.devmadesimple.jobtracker.entity.User;
import com.devmadesimple.jobtracker.exception.ApplicationNotFoundException;
import com.devmadesimple.jobtracker.exception.CompanyNotFoundException;
import com.devmadesimple.jobtracker.mapper.ApplicationMapper;
import com.devmadesimple.jobtracker.repository.ApplicationRepository;
import com.devmadesimple.jobtracker.repository.CompanyRepository;
import com.devmadesimple.jobtracker.repository.StatusHistoryRepository;
import com.devmadesimple.jobtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ApplicationMapper applicationMapper;

    @Autowired
    StatusHistoryRepository statusHistoryRepository;

    @Autowired
    CurrentUserProvider currentUserProvider;

    public Long currentUserId() {
        return currentUserProvider.getCurrentUser().getId();
    }

    @Transactional
    @CacheEvict(value = "dashboard", key = "#root.target.currentUserId()")
    public ApplicationResponse createApplication(ApplicationRequest request) {
        Application application = new Application();
        application.setJobTitle(request.getJobTitle());
        application.setJobDescription(request.getJobDescription());
        application.setDateApplied(request.getDateApplied());
        application.setSource(request.getSource());
        application.setMinSalary(request.getMinSalary());
        application.setMaxSalary(request.getMaxSalary());
        application.setCurrency(request.getCurrency());
        application.setLocation(request.getLocation());
        application.setStatus(request.getStatus());

        if (request.getCompanyId() != null) {
            Company company = companyRepository.findById(request.getCompanyId())
                    .orElseThrow(() -> new CompanyNotFoundException("Company not found with id " + request.getCompanyId()));
            application.setCompany(company);
        } else if (request.getCompanyName() != null) {
            Company company = new Company();
            company.setName(request.getCompanyName());
            company.setUser(currentUserProvider.getCurrentUser());
            application.setCompany(companyRepository.save(company));
        }

        application.setUser(currentUserProvider.getCurrentUser());

        Application response = applicationRepository.save(application);
        return applicationMapper.toResponse(response);
    }

    public Page<ApplicationResponse> getApplications(Pageable pageable) {
        Long userId = currentUserProvider.getCurrentUser().getId();
        Page<Application> response = applicationRepository.findByUser_Id(userId, pageable);
        return response.map(applicationMapper::toResponse);
    }

    public ApplicationResponse getApplicationById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id " + id));

        if (!application.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new ApplicationNotFoundException("Application not found with id " + id);
        }

        return applicationMapper.toResponse(application);
    }

    @CacheEvict(value = "dashboard", key = "#root.target.currentUserId()")
    @Transactional
    public ApplicationResponse updateApplication(ApplicationRequest request, Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id " + id));

        if (!application.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new ApplicationNotFoundException("Application not found with id " + id);
        }

        application.setJobTitle(request.getJobTitle());
        application.setJobDescription(request.getJobDescription());
        application.setDateApplied(request.getDateApplied());
        application.setSource(request.getSource());
        application.setMinSalary(request.getMinSalary());
        application.setMaxSalary(request.getMaxSalary());
        application.setCurrency(request.getCurrency());
        application.setLocation(request.getLocation());
        application.setStatus(request.getStatus());

        if (request.getCompanyId() != null) {
            Company company = companyRepository.findById(request.getCompanyId())
                    .orElseThrow(() -> new CompanyNotFoundException("Company not found with id " + request.getCompanyId()));
            application.setCompany(company);
        } else if (request.getCompanyName() != null) {
            Company company = new Company();
            company.setName(request.getCompanyName());
            company.setUser(currentUserProvider.getCurrentUser());
            application.setCompany(companyRepository.save(company));
        }

        Application response = applicationRepository.save(application);
        return applicationMapper.toResponse(response);
    }

    @Transactional
    @CacheEvict(value = "dashboard", key = "#root.target.currentUserId()")
    public ApplicationResponse patchApplication(StatusUpdateRequest request, Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id " + id));

        if (!application.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new ApplicationNotFoundException("Application not found with id " + id);
        }

        String oldStatus = application.getStatus();
        application.setStatus(request.getStatus());
        Application response = applicationRepository.save(application);

        StatusHistory history = new StatusHistory();
        history.setOldStatus(oldStatus);
        history.setNewStatus(request.getStatus());
        history.setApplication(application);
        statusHistoryRepository.save(history);

        return applicationMapper.toResponse(response);
    }

    @CacheEvict(value = "dashboard", key = "#root.target.currentUserId()")
    public void deleteApplication(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id " + id));

        if (!application.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new ApplicationNotFoundException("Application not found with id " + id);
        }

        applicationRepository.deleteById(id);
    }
}