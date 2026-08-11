package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.request.CompanyRequest;
import com.devmadesimple.jobtracker.dto.response.CompanyResponse;
import com.devmadesimple.jobtracker.entity.Company;
import com.devmadesimple.jobtracker.exception.CompanyNotFoundException;
import com.devmadesimple.jobtracker.mapper.CompanyMapper;
import com.devmadesimple.jobtracker.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    CurrentUserProvider currentUserProvider;

    public CompanyResponse createCompany(CompanyRequest request) {
        Company company = new Company();
        company.setName(request.getName());
        company.setIndustry(request.getIndustry());
        company.setWebsite(request.getWebsite());
        company.setNotes(request.getNotes());
        company.setCareerPageUrl(request.getCareerPageUrl());
        company.setUser(currentUserProvider.getCurrentUser());
        Company response = companyRepository.save(company);
        return companyMapper.toResponse(response);
    }

    public Page<CompanyResponse> getCompanies(Pageable pageable) {
        Long userId = currentUserProvider.getCurrentUser().getId();
        Page<Company> responses = companyRepository.findByUser_Id(userId, pageable);
        return responses.map(companyMapper::toResponse);
    }

    public CompanyResponse getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id " + id));
        if (!company.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new CompanyNotFoundException("Company not found with id " + id);
        }
        return companyMapper.toResponse(company);
    }

    public CompanyResponse updateCompany(CompanyRequest request, Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id " + id));
        if (!company.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new CompanyNotFoundException("Company not found with id " + id);
        }
        company.setName(request.getName());
        company.setIndustry(request.getIndustry());
        company.setWebsite(request.getWebsite());
        company.setNotes(request.getNotes());
        company.setCareerPageUrl(request.getCareerPageUrl());
        Company response = companyRepository.save(company);
        return companyMapper.toResponse(response);
    }

    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id " + id));
        if (!company.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new CompanyNotFoundException("Company not found with id " + id);
        }
        companyRepository.delete(company);
    }
}