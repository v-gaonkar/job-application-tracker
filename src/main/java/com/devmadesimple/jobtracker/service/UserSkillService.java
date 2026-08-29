package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.response.SkillResponse;
import com.devmadesimple.jobtracker.entity.Skill;
import com.devmadesimple.jobtracker.entity.User;
import com.devmadesimple.jobtracker.entity.UserSkill;
import com.devmadesimple.jobtracker.exception.SkillNotFoundException;
import com.devmadesimple.jobtracker.mapper.SkillMapper;
import com.devmadesimple.jobtracker.repository.SkillRepository;
import com.devmadesimple.jobtracker.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSkillService {

    @Autowired
    UserSkillRepository userSkillRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillMapper skillMapper;

    @Autowired
    CurrentUserProvider currentUserProvider;

    public void addSkillToUser(Long skillId) {
        User user = currentUserProvider.getCurrentUser();
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNotFoundException("Skill not found with id " + skillId));

        if (userSkillRepository.existsByUser_IdAndSkill_Id(user.getId(), skillId)) {
            return;
        }

        UserSkill userSkill = new UserSkill();
        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkillRepository.save(userSkill);
    }

    public void removeSkillFromUser(Long skillId) {
        User user = currentUserProvider.getCurrentUser();
        userSkillRepository.deleteByUser_IdAndSkill_Id(user.getId(), skillId);
    }

    public List<SkillResponse> getSkillsForUser() {
        User user = currentUserProvider.getCurrentUser();
        return userSkillRepository.findByUser_Id(user.getId()).stream()
                .map(us -> skillMapper.toResponse(us.getSkill()))
                .collect(Collectors.toList());
    }
}