package com.edu.api.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edu.dto.ProductDTO;
import com.edu.service.ProductService;

@RestController(value = "productAPIofAdmin")
public class ProductAPI {
	@Autowired
	private ProductService productService;
	@DeleteMapping("/api/product")
	public void deleteNew(@RequestBody long[] ids) {
		System.out.println("delete");
		productService.delete(ids);
		
	}
	
	@GetMapping("/api/product/getpopular")
	public List<ProductDTO> getProduct() {
		List<ProductDTO> lists = new ArrayList<>();
		lists = productService.getALL();
		return lists;
	}
}
