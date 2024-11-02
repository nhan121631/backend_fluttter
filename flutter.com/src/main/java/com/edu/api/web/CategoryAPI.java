package com.edu.api.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.converter.CategoryConverter;
import com.edu.dto.CategoryDTO;
import com.edu.entity.CategoryEntity;
import com.edu.service.CategoryService;

@RestController(value = "categoriesAPIOfWeb")
public class CategoryAPI {
	@Autowired 
	private CategoryService categoryService;
	@Autowired
	private CategoryConverter categoryConverter;

    @GetMapping("/api/category/get")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> lists = new ArrayList<>();
        for (CategoryEntity item : categoryService.getCategories()) {
			lists.add(categoryConverter.toDto(item));
		}
        		   	
        return ResponseEntity.ok(lists);
    }

}
