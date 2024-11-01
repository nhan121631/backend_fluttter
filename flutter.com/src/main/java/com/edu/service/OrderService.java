package com.edu.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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
	
	public List<OrderEntity> findAll(){
		return orderRepository.findAll();
	}
	
	public List<OrderEntity> findByUserIdAndStatusIsNot(Long user_id, int status) {
		return orderRepository.findByUserIdAndStatusIsNot(user_id, status);
	}
	
	public OrderEntity findOneById(Long id) {
		return orderRepository.findOne(id);
	}
	
	@Transactional
	public void updateStatus(Long id) {
		 orderRepository.updateStatus(id);
	}
	

}
