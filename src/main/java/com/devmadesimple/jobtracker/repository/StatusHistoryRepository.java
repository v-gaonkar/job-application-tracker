package com.devmadesimple.jobtracker.repository;

import com.devmadesimple.jobtracker.entity.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {
    List<StatusHistory> findByApplication_IdOrderByChangedAtAsc(Long applicationId);

    @Query(value = """
    SELECT * FROM (
        SELECT
            sh.*,
            ROW_NUMBER() OVER (PARTITION BY sh.application_id ORDER BY sh.changed_at DESC) AS rn
        FROM status_history sh
        WHERE sh.application_id IN (:applicationIds)
    ) ranked
    WHERE rn = 1
    """, nativeQuery = true)
    List<StatusHistory> findLatestPerApplication(@Param("applicationIds") List<Long> applicationIds);
}

