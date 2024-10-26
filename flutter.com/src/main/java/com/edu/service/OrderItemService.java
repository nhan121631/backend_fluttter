package com.edu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.entity.OrderitemEntity;
import com.edu.repository.OrderItemRepository;

@Service
public class OrderItemService {
@Autowired OrderItemRepository orderItemRepository;
	 public void saveAll(List<OrderitemEntity> orderItems) {
		 for (OrderitemEntity orderitemEntity : orderItems) {
	         orderItemRepository.save(orderitemEntity);
		}
	    }
}
