package com.edu.api.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.entity.ProductEntity;
import com.edu.service.ProductService;

@RestController(value = "produceAPIOfWeb")
public class ProductAPI {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/api/product/search")
	public ResponseEntity<List<ProductEntity>> findProduct(@RequestParam("param")String param){
		List<ProductEntity> products = productService.findProduct(param);    	
        return ResponseEntity.ok(products);
	}
	
		
}
