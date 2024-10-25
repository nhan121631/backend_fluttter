package com.edu.api.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.entity.CategoryEntity;
import com.edu.service.CategoryService;

@RestController(value = "categoriesAPIOfWeb")
public class CategoryAPI {
	@Autowired 
	private CategoryService categoryService;

    @GetMapping("/api/category/get")
    public ResponseEntity<List<CategoryEntity>> getCategories() {
        List<CategoryEntity> lists = categoryService.getCategories();    	
        return ResponseEntity.ok(lists);
    }

}
