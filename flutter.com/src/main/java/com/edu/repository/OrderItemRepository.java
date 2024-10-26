package com.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.OrderitemEntity;

public interface OrderItemRepository  extends JpaRepository<OrderitemEntity, Long>{

	///List<OrderitemEntity> saveAll(List<OrderitemEntity>);
}
