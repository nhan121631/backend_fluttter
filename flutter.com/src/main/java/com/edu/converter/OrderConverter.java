package com.edu.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edu.dto.OrderDTO;
import com.edu.dto.OrderItemDTO;
import com.edu.entity.OrderEntity;
import com.edu.entity.OrderitemEntity;

@Component
public class OrderConverter {
	
	@Autowired
	private OrderItemConverter orderItemConverter;
	public OrderDTO toDto(OrderEntity entity) {
		OrderDTO result = new OrderDTO();
	//	result.setUser(entity.getUser());
		result.setId(entity.getId());
		
		List<OrderItemDTO> lists = new ArrayList<>();
		for (OrderitemEntity item : entity.getOrderitems()) {
			lists.add(orderItemConverter.toDto(item));
		}
		result.setOrderitems(lists);
		result.setFullname(entity.getFullname());
		result.setPhone(entity.getPhone());
		result.setAddress(entity.getAddress());
		result.setNote(entity.getNote());
		result.setStatus(entity.getStatus());
		result.setPayment(entity.getPayment());
		result.setTotalPrice(entity.getTotalPrice());
		result.setCreatedDate(entity.getCreatedDate());
		result.setModifiedDate(entity.getModifiedDate());
		
		return result;
	}
}
