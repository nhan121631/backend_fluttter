package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
	RoleEntity findByCode(String code);
}
