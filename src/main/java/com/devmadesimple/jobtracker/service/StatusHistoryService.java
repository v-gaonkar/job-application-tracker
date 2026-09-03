package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.response.StatusHistoryResponse;
import com.devmadesimple.jobtracker.entity.Application;
import com.devmadesimple.jobtracker.entity.StatusHistory;
import com.devmadesimple.jobtracker.exception.ApplicationNotFoundException;
import com.devmadesimple.jobtracker.mapper.StatusHistoryMapper;
import com.devmadesimple.jobtracker.repository.ApplicationRepository;
import com.devmadesimple.jobtracker.repository.StatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatusHistoryService {

    @Autowired
    StatusHistoryRepository statusHistoryRepository;

    @Autowired
    StatusHistoryMapper statusHistoryMapper;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    CurrentUserProvider currentUserProvider;

    public List<StatusHistoryResponse> getHistoryForApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id " + applicationId));
        if (!application.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new ApplicationNotFoundException("Application not found with id " + applicationId);
        }

        return statusHistoryRepository.findByApplication_IdOrderByChangedAtAsc(applicationId).stream()
                .map(statusHistoryMapper::toResponse)
                .toList();
    }

    public Map<Long, StatusHistory> getLatestStatusHistoryByApplicationIds(List<Long> applicationIds) {
        List<StatusHistory> latestStatus = statusHistoryRepository.findLatestPerApplication(applicationIds);
        return latestStatus.stream().collect(Collectors.toMap(s -> s.getApplication().getId(), s -> s));
    }
}