package com.devmadesimple.jobtracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="rounds",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "application_id",
                        "round",
                        "interview_date"
                })
        }
)
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Column(nullable = false)
    private String round;

    @Column
    private LocalDateTime interviewDate;

    @Column
    private String status;

    @Column
    private String notes;
}
