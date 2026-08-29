package com.devmadesimple.jobtracker.repository;

import com.devmadesimple.jobtracker.entity.UserSkill;
import com.devmadesimple.jobtracker.entity.UserSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, UserSkillId> {
    List<UserSkill> findByUser_Id(Long userId);
    void deleteByUser_IdAndSkill_Id(Long userId, Long skillId);
    boolean existsByUser_IdAndSkill_Id(Long userId, Long skillId);
}
