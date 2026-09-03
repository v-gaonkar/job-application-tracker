package com.devmadesimple.jobtracker.repository;

import com.devmadesimple.jobtracker.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    Optional<EmailTemplate> findByCategoryAndName(String category, String name);

}
