package com.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
//	List<OrderEntity> getAll();

}