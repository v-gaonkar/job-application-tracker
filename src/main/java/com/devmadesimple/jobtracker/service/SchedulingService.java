package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.entity.*;
import com.devmadesimple.jobtracker.exception.EmailTemplateNotFoundException;
import com.devmadesimple.jobtracker.repository.ApplicationRepository;
import com.devmadesimple.jobtracker.repository.EmailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class SchedulingService {
    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Autowired
    StatusHistoryService statusHistoryService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    NotificationLogService notificationlogService;

    @Autowired
    RoundService roundService;

    @Scheduled(cron = "0 0 10 * * *")
    public void scheduleStaleUserReminder() {
        Map<String, Long> thresholds = Map.of(
                "Applied", 15L,
                "Under Process", 15L,
                "Interview", 7L
        );
        Map<String, String> templates = Map.of(
                "Applied", "Applied Stale Reminder",
                "Under Process", "Under Process Stale Reminder",
                "Interview", "Interview Stale Reminder"
        );

        List<Application> applications = applicationRepository.findByStatusIn(
                List.of("Applied", "Under Process", "Interview"));
        List<Long> applicationIds = applications.stream().map(Application::getId).toList();

        Map<Long, StatusHistory> latestStatus = statusHistoryService.getLatestStatusHistoryByApplicationIds(applicationIds);
        Map<Long, NotificationLog> staleNotifications = notificationlogService.getStaleNotificationByApplicationIds(applicationIds);

        for (Application application : applications) {
            StatusHistory latest = latestStatus.get(application.getId());
            if (latest == null) {
                continue;
            }

            String status = latest.getNewStatus();
            long daysSince = ChronoUnit.DAYS.between(latest.getChangedAt(), LocalDateTime.now());

            if (daysSince < thresholds.get(status)) {
                continue;
            }

            String type = "stale-reminder-" + status.toLowerCase().replace(" ", "-");
            NotificationLog lastReminder = staleNotifications.get(application.getId());

            if (lastReminder != null && lastReminder.getType().equals(type)) {
                long daysSinceLastReminder = ChronoUnit.DAYS.between(lastReminder.getSentAt(), LocalDateTime.now());
                if (daysSinceLastReminder < 4) {
                    continue;
                }
            }

            sendFollowUp(application, templates.get(status), daysSince);
            notificationlogService.addNotificationLog(null, application, type);
        }
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void scheduleInterviewReminder() {
        List<Round> rounds = roundService.getRoundsWithinRange(LocalDateTime.now(), LocalDateTime.now().plusDays(7));

        for (Round round : rounds) {
            long daysLeft = ChronoUnit.DAYS.between(LocalDateTime.now(), round.getInterviewDate());

            String type = null;
            String templateName = null;

            if (daysLeft >= 1 && daysLeft <= 2) {
                type = "interview-reminder-soon";
                templateName = "Interview Reminder Soon";
            } else if (daysLeft >= 5 && daysLeft <= 7) {
                type = "interview-reminder-week";
                templateName = "Interview Reminder Week";
            }

            if (type == null) {
                continue;
            }

            if (notificationlogService.existsByRoundAndType(round.getId(), type)) {
                continue;
            }

            sendFollowUp(round.getApplication(), templateName, daysLeft);
            notificationlogService.addNotificationLog(round, round.getApplication(), type);
        }
    }

    private void sendFollowUp(Application application, String templateName, long daysSince) {
        EmailTemplate template = emailTemplateRepository.findByCategoryAndName("system_reminder", templateName)
                .orElseThrow(() -> new EmailTemplateNotFoundException("Missing system template: " + templateName));

        String subject = fillPlaceholders(template.getSubject(), application, daysSince);
        String body = fillPlaceholders(template.getBody(), application, daysSince);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com");
        message.setTo(application.getUser().getEmail());
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

    private String fillPlaceholders(String text, Application application, long daysSince) {
        return text
                .replace("{{userName}}", application.getUser().getUsername())
                .replace("{{jobTitle}}", application.getJobTitle())
                .replace("{{companyName}}", application.getCompany().getName())
                .replace("{{daysSince}}", String.valueOf(daysSince));
    }

}
