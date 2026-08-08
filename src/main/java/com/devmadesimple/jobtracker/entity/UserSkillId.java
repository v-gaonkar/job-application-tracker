package com.devmadesimple.jobtracker.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class UserSkillId implements Serializable {

    private Long userId;
    private Long skillId;
}
