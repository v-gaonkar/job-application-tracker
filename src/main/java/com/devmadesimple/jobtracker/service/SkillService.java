package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.request.SkillRequest;
import com.devmadesimple.jobtracker.dto.response.SkillResponse;
import com.devmadesimple.jobtracker.entity.Skill;
import com.devmadesimple.jobtracker.exception.SkillNameAlreadyExistsException;
import com.devmadesimple.jobtracker.exception.SkillNotFoundException;
import com.devmadesimple.jobtracker.mapper.SkillMapper;
import com.devmadesimple.jobtracker.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillMapper skillMapper;

    //create
    public SkillResponse createSkill(SkillRequest request) {
        Skill skill = new Skill();
        if(skillRepository.existsByName(request.getName())) {
            throw new SkillNameAlreadyExistsException("Skill with name " + request.getName() + "already exists");
        }
        skill.setName(request.getName());
        Skill response = skillRepository.save(skill);
        return skillMapper.toResponse(response);
    }

    //get all
    public List<SkillResponse> getSkills() {
        List<Skill> responses = skillRepository.findAll();
        return responses.stream().map(skillMapper::toResponse).toList();
    }

    //update
//    public SkillResponse updateCompany(SkillRequest request, Long id) {
//        Skill skill = skillRepository.findById(id)
//                .orElseThrow(() -> new SkillNotFoundException("Company not found with id " + id));
//        skill.setName(request.getName());
//        Skill response = skillRepository.save(skill);
//
//        return skillMapper.toResponse(response);
//    }


    //delete
    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException("Skill not found with id " + id));
        skillRepository.delete(skill);
    }
}
