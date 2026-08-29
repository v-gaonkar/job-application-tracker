package com.devmadesimple.jobtracker.repository;

import com.devmadesimple.jobtracker.entity.ApplicationSkill;
import com.devmadesimple.jobtracker.entity.ApplicationSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ApplicationSkillRepository extends JpaRepository<ApplicationSkill, ApplicationSkillId> {
    boolean existsByApplication_IdAndSkill_Id(Long applicationId, Long skillId);

    void deleteByApplication_IdAndSkill_Id(Long applicationId, Long skillId);

    List<ApplicationSkill> findByApplication_Id(Long applicationId);
}
