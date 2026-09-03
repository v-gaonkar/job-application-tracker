package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.response.NotificationLogResponse;
import com.devmadesimple.jobtracker.entity.Application;
import com.devmadesimple.jobtracker.entity.NotificationLog;
import com.devmadesimple.jobtracker.entity.Round;
import com.devmadesimple.jobtracker.exception.ApplicationNotFoundException;
import com.devmadesimple.jobtracker.mapper.NotificationLogMapper;
import com.devmadesimple.jobtracker.repository.ApplicationRepository;
import com.devmadesimple.jobtracker.repository.NotificationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationLogService {

    @Autowired
    NotificationLogRepository notificationLogRepository;

    @Autowired
    NotificationLogMapper notificationLogMapper;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    CurrentUserProvider currentUserProvider;

    public void addNotificationLog(Round round, Application application, String type) {
        NotificationLog notificationLog = new NotificationLog();
        notificationLog.setType(type);
        notificationLog.setRound(round);
        notificationLog.setApplication(application);
        notificationLogRepository.save(notificationLog);
    }

    public List<NotificationLogResponse> getNotificationsForApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id " + applicationId));
        if (!application.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new ApplicationNotFoundException("Application not found with id " + applicationId);
        }

        return notificationLogRepository.findByApplication_IdOrderBySentAtDesc(applicationId).stream()
                .map(notificationLogMapper::toResponse)
                .toList();
    }

    public Map<Long, NotificationLog> getStaleNotificationByApplicationIds(List<Long> applicationIds) {
        List<NotificationLog> staleNotification = notificationLogRepository.findStaleNotificationPerApplication(applicationIds);
        return staleNotification.stream().collect(Collectors.toMap(n -> n.getApplication().getId(), n -> n));
    }

    public boolean existsByRoundAndType(Long roundId, String type) {
        return notificationLogRepository.existsByRound_IdAndType(roundId, type);
    }
}