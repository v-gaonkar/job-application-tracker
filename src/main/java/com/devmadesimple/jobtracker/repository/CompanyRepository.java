package com.devmadesimple.jobtracker.repository;

import com.devmadesimple.jobtracker.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Page<Company> findByUser_Id(Long userId, Pageable pageable);
}
