package com.edu.api.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.dto.ProductDTO;
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
	@GetMapping("/api/product/getpopular")
	public List<ProductDTO> getProduct() {
		List<ProductDTO> lists = new ArrayList<>();
		lists = productService.getALL();
		return lists;
	}
	
	@GetMapping("/api/product/getfilter")
	public List<ProductDTO> getFilterProduct(@RequestParam("sell1")double sell1, 
			@RequestParam("sell2") double sell2) {
		List<ProductDTO> lists = new ArrayList<>();
		lists = productService.getFilterProduct(sell1, sell2);
		return lists;
	}
		
}
