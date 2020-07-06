package com.learning.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.springboot.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
