package com.edu.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edu.dto.CategoryDTO;
import com.edu.dto.ProductDTO;
import com.edu.entity.CategoryEntity;
import com.edu.entity.ProductEntity;

@Component
public class CategoryConverter {
	@Autowired
	private ProductConverter productConverter;
	public CategoryDTO toDto(CategoryEntity entity) {
		CategoryDTO result = new CategoryDTO();
		result.setId(entity.getId());
		result.setName(entity.getName());
		List<ProductDTO> p = new ArrayList<>();
		for (ProductEntity item: entity.getNews()) {
			p.add(productConverter.toDto(item));
		}
		result.setNews(p);
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
