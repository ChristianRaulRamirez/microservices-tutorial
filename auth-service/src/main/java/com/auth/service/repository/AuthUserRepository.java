package com.auth.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.service.entity.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer>{

	Optional<AuthUser> findByUserName(String username);
	
}
