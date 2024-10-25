package com.edu.api.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.entity.OrderEntity;
import com.edu.service.OrderService;

@RestController(value = "orderAPIOfWeb")
public class OrderAPI {
	@Autowired OrderService orderService;

    @GetMapping("/api/order")
    public ResponseEntity<List<OrderEntity>> getOrder() {
        List<OrderEntity> orders = orderService.getAll();    	
        return ResponseEntity.ok(orders);
    }
    
}
