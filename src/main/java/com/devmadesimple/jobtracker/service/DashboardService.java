package com.devmadesimple.jobtracker.service;
import com.devmadesimple.jobtracker.dto.response.DashboardResponse;
import com.devmadesimple.jobtracker.entity.Application;
import com.devmadesimple.jobtracker.entity.Round;
import com.devmadesimple.jobtracker.entity.StatusHistory;
import com.devmadesimple.jobtracker.repository.ApplicationRepository;
import com.devmadesimple.jobtracker.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    CurrentUserProvider currentUserProvider;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    StatusHistoryService statusHistoryService;


    public long getTotalApplications() {
        Long userId = currentUserProvider.getCurrentUser().getId();
        return applicationRepository.countByUser_Id(userId);
    }

    public Map<String, Long> countByStatusForUser() {
        List<Object[]> results = applicationRepository.countByStatusForUser(currentUserProvider.getCurrentUser().getId());
        return results.stream().collect(Collectors.toMap(
                row -> (String) row[0],
                row -> ((Number) row[1]).longValue()
        ));
    }

    public long getUpcomingInterviewsCount() {
        Long userId = currentUserProvider.getCurrentUser().getId();
        LocalDateTime now = LocalDateTime.now();
        List<Round> rounds = roundRepository.findByInterviewDateBetweenAndApplication_User_Id(now, now.plusDays(7), userId);
        return rounds.size();
    }

    public long getStaleApplicationsCount() {
        Map<String, Long> thresholds = Map.of("Applied", 15L, "Under Process", 15L, "Interview", 7L);

        Long userId = currentUserProvider.getCurrentUser().getId();
        List<Application> applications = applicationRepository.findByUser_IdAndStatusIn(
                userId, List.of("Applied", "Under Process", "Interview"));
        List<Long> applicationIds = applications.stream().map(Application::getId).toList();

        Map<Long, StatusHistory> latestStatus = statusHistoryService.getLatestStatusHistoryByApplicationIds(applicationIds);

        long count = 0;
        for (Application application : applications) {
            StatusHistory latest = latestStatus.get(application.getId());
            if (latest == null) continue;

            long daysSince = ChronoUnit.DAYS.between(latest.getChangedAt(), LocalDateTime.now());
            if (daysSince >= thresholds.get(latest.getNewStatus())) {
                count++;
            }
        }
        return count;
    }

    public Map<String, Long> getApplicationsPerWeek() {
        List<Object[]> results = applicationRepository.countByWeekForUser(currentUserProvider.getCurrentUser().getId());
        return results.stream().collect(Collectors.toMap(
                row -> row[0].toString(),
                row -> ((Number) row[1]).longValue()
        ));
    }

    public Map<String, Long> getStatusBreakdown() {
        List<Object[]> results = applicationRepository.countByStatusForUser(currentUserProvider.getCurrentUser().getId());
        return results.stream().collect(Collectors.toMap(
                row -> (String) row[0],
                row -> ((Number) row[1]).longValue()
        ));
    }

    public Long currentUserId() {
        return currentUserProvider.getCurrentUser().getId();
    }

    @Cacheable(value = "dashboard", key = "#root.target.currentUserId()")
    public DashboardResponse getDashboard() {
        DashboardResponse response = new DashboardResponse();
        response.setTotalApplications(getTotalApplications());
        response.setStatusBreakdown(getStatusBreakdown());
        response.setUpcomingInterviewsCount(getUpcomingInterviewsCount());
        response.setStaleApplicationsCount(getStaleApplicationsCount());
        response.setApplicationsPerWeek(getApplicationsPerWeek());
        return response;
    }

}
