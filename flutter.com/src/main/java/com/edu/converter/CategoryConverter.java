package com.edu.converter;

import org.springframework.stereotype.Component;

import com.edu.dto.CategoryDTO;
import com.edu.entity.CategoryEntity;

@Component
public class CategoryConverter {
	
	public CategoryDTO toDto(CategoryEntity entity) {
		CategoryDTO result = new CategoryDTO();
		result.setId(entity.getId());
		result.setName(entity.getName());
		return result;
	}
	
	public CategoryEntity toEntity(CategoryDTO dto) {
		CategoryEntity result = new CategoryEntity();
		result.setId(dto.getId());
		result.setName(dto.getName());
		return result;
	}
	
	public CategoryEntity toEntity(CategoryEntity result, CategoryDTO dto) {
		//CategoryEntity result = new CategoryEntity()
		result.setId(dto.getId());
		result.setName(dto.getName());
		return result;
	}
}
