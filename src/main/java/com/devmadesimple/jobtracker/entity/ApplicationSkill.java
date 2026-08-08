package com.devmadesimple.jobtracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "application_skills")
public class ApplicationSkill {
    @EmbeddedId
    private ApplicationSkillId id = new ApplicationSkillId();

    @ManyToOne(optional = false)
    @MapsId("applicationId")
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne(optional = false)
    @MapsId("skillId")
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
}