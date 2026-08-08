package com.devmadesimple.jobtracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="contacts",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email",
                "company_id"
        })
        }
)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Column
    private String role;

    @Column
    private String phoneNo;

    @Column
    private String notes;

    @Column(name="created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
