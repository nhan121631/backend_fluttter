package com.edu.api.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.edu.converter.ProductConverter;
import com.edu.dto.ProductDTO;
import com.edu.entity.ProductEntity;
import com.edu.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController(value = "produceAPIOfWeb")
public class ProductAPI {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductConverter productConverter;
	
	@GetMapping("/api/product/search")
	public ResponseEntity<List<ProductDTO>> findProduct(@RequestParam("param")String param){
		List<ProductEntity> products = productService.findProduct(param);   
		List<ProductDTO> lists = new ArrayList<>();
		for (ProductEntity item : products) {
		lists.add(productConverter.toDto(item));	
		}
        return ResponseEntity.ok(lists);
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
	
	@GetMapping("/api/product/getrecommend")
	public List<ProductDTO> getRecommend(@RequestParam("user_id") Long user_id) throws JsonProcessingException, IOException{
		
		  List<Long> productIds = new ArrayList<>();
		  List<ProductDTO> list = new ArrayList<>();

		    String url = "http://127.0.0.1:5000/recommend?user_id=" + user_id;
		    RestTemplate restTemplate = new RestTemplate();
		    String response = restTemplate.getForObject(url, String.class);

		    // Sử dụng Jackson để xử lý JSON
		    ObjectMapper objectMapper = new ObjectMapper();
		    JsonNode jsonResponse = objectMapper.readTree(response);
		    JsonNode recommendedIds = jsonResponse.get("recommended_product_ids");

		    for (JsonNode idNode : recommendedIds) {
		        productIds.add(idNode.asLong());
		    }
		    for(Long p:productIds) {
		    	System.out.println(p);
		    	list.add(productService.findById(p));
		    }	
		    System.out.println("====");
		return list;
	}
		
}
