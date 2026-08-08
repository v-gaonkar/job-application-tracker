package com.devmadesimple.jobtracker.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ApplicationSkillId {

    private Long applicationId;
    private Long skillId;
}
