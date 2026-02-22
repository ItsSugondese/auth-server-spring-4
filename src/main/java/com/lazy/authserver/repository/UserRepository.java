package com.lazy.authserver.repository;

import com.lazy.authserver.entity.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SecurityUser, Integer>{

	SecurityUser findByUsername(String username);
}
