package com.learning.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.springboot.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
