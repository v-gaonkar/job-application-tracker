package com.devmadesimple.jobtracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ai_match_results")
public class AIMatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Column(name = "match_score")
    private Integer matchScore;

    @Column(name = "interview_tips", columnDefinition = "TEXT")
    private String interviewTips;

    @Column(name = "generated_at", insertable = false, updatable = false)
    private LocalDateTime generatedAt;
}