package com.devmadesimple.jobtracker.repository;

import com.devmadesimple.jobtracker.entity.AIMatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AIMatchResultRepository extends JpaRepository<AIMatchResult, Long> {
    Optional<AIMatchResult> findByApplication_Id(Long applicationId);
}