package com.devmadesimple.jobtracker.repository;

import com.devmadesimple.jobtracker.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findByCompany_Id(Long companyId, Pageable pageable);
    long countByCompany_Id(Long companyId);
}