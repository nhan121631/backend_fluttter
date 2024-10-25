package com.edu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.CartEntity;


public interface CartRepository extends JpaRepository<CartEntity, Long> {
	
	Optional<CartEntity> findByUserId(Long id);
	
	



}
