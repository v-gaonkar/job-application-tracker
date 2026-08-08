package com.devmadesimple.jobtracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="applications",
uniqueConstraints = {
@UniqueConstraint(columnNames = {
        "user_id",
        "company_id",
        "job_title",
        "date_applied"
})
        })
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name="company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String jobTitle;

    @Column
    private String jobDescription;

    @Column(nullable = false)
    private LocalDateTime dateApplied;

    @Column
    private String source;

    @Column
    private Integer minSalary;

    @Column
    private Integer maxSalary;

    @Column
    private String currency;

    @Column
    private String location;

    @Column
    private String status;

    @Column(name="created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

}
