package com.edu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.entity.OrderEntity;
import com.edu.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	public List<OrderEntity> getAll() {
	    List<OrderEntity> items = orderRepository.findAll();
	    
	    return items != null ? items : new ArrayList<>();
	}

	public OrderEntity save(OrderEntity order) {
		return orderRepository.save(order);
	}
	
	

}
