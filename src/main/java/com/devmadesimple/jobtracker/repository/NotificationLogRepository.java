package com.devmadesimple.jobtracker.repository;

import com.devmadesimple.jobtracker.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
    List<NotificationLog> findByApplication_IdOrderBySentAtDesc(Long applicationId);
    @Query(value= """
            SELECT * FROM (
                        SELECT nl.*,
                        ROW_NUMBER() OVER (PARTITION BY nl.application_id ORDER BY nl.sent_at DESC) AS rn
                        FROM notification_logs nl
                        WHERE nl.application_id IN (:applicationIds)
                        AND nl.type LIKE 'stale-reminder-%'
            ) ranked
            WHERE rn=1
            """,  nativeQuery = true)
    List<NotificationLog> findStaleNotificationPerApplication(List<Long> applicationIds);

    boolean existsByRound_IdAndType(Long roundId, String type);
}