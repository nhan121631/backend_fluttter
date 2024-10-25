package com.edu.api.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edu.dto.CategoryDTO;
import com.edu.service.CategoryService;

@RestController(value = "categoryAPIofAdmin")
public class CategoryAPI {
	
	@Autowired 
	private CategoryService categoryService;
	
	@PostMapping("/api/category")
	public CategoryDTO createNew(@RequestBody CategoryDTO categoryDTO) {
		return categoryService.save(categoryDTO);
	}
	@PutMapping("/api/category")
	public CategoryDTO updateNew(@RequestBody CategoryDTO updatecategory) {
		return categoryService.save(updatecategory);
	}
	
	@DeleteMapping("/api/category")
	public void deleteNew(@RequestBody long[] ids) {
		System.out.println("delete");
		categoryService.delete(ids);
		
	}
//	
//	@PostMapping("/api/checkcode")
//	public ResponseEntity<Map<String, Boolean>> checkCode(@RequestBody Map<String, String> payload) {
//	    String code = payload.get("code");
//	    boolean exists = categoryService.isCodeExists(code); 
//	    Map<String, Boolean> response = new HashMap<>();
//	    response.put("exists", exists);
//	    return ResponseEntity.ok(response);
//	}

}
