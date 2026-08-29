package com.devmadesimple.jobtracker.repository;

import com.devmadesimple.jobtracker.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findByApplication_Id(Long applicationId);

    List<Round> findByInterviewDateBetween(LocalDateTime start, LocalDateTime end);

    List<Round> findByInterviewDateBetweenAndApplication_User_Id(LocalDateTime start, LocalDateTime end, Long userId);

    List<Round> findByApplication_User_Id(Long userId);
}
