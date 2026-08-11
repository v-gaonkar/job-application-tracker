package com.devmadesimple.jobtracker.repository;

import com.devmadesimple.jobtracker.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStatusIn(List<String> statuses);

    Page<Application> findByUser_Id(Long userId, Pageable pageable);

    long countByUser_Id(Long userId);

    @Query("SELECT a.status, COUNT(a) FROM Application a WHERE a.user.id = :userId GROUP BY a.status")
    List<Object[]> countByStatusForUser(@Param("userId") Long userId);

    List<Application> findByUser_IdAndStatusIn(Long userId, List<String> statuses);

    @Query(value = """
    SELECT DATE_TRUNC('week', date_applied) AS week, COUNT(*)
    FROM applications
    WHERE user_id = :userId
    GROUP BY week
    ORDER BY week
    """, nativeQuery = true)
    List<Object[]> countByWeekForUser(@Param("userId") Long userId);

}
