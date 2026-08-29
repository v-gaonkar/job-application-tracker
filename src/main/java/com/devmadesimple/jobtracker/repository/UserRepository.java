package com.devmadesimple.jobtracker.repository;

import com.devmadesimple.jobtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);

    User findByEmail(String username);
}
