package com.edu.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edu.dto.OrderItemDTO;
import com.edu.dto.ProductDTO;
import com.edu.entity.OrderitemEntity;

@Component
public class OrderItemConverter {
	@Autowired
	private ProductConverter productConverter;
    public OrderItemDTO toDto(OrderitemEntity entity) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(entity.getId());
        ProductDTO p = productConverter.toDto(entity.getProduct());
        dto.setProduct(p); 
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        dto.setTotalPrice(entity.getTotalPrice());
        return dto;
    }
}
