package com.edu.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.converter.CategoryConverter;
import com.edu.dto.CategoryDTO;
import com.edu.entity.CategoryEntity;
import com.edu.repository.CategoryRepository;
import com.edu.util.SecurityUtils;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryConverter categoryConverter;
	
	public List<CategoryEntity> getCategories(){
		List<CategoryEntity> lists = categoryRepository.findAll();
		return lists;
	}
	
	public Map<Long, String> findAll() {
		Map<Long, String>  result = new HashMap<>();
		List<CategoryEntity> entities = categoryRepository.findAll();
		for(CategoryEntity item:entities) {
			//CategoryDTO dto = categoryConverter.toDto(item);
			result.put(item.getId(), item.getName());
		}
		
		return result;
	}


	public List<CategoryDTO> getAll(Pageable pageable) {
		List<CategoryDTO> models = new ArrayList<>();
		List<CategoryEntity> entities = categoryRepository.findAll(pageable).getContent();
		for (CategoryEntity item: entities) {
			CategoryDTO CategoryDTO = categoryConverter.toDto(item);
			models.add(CategoryDTO);
		}
		return models;
	}

	public int getTotalItem() {
		return (int) categoryRepository.count();

	}

	public CategoryDTO findById(long id) {
		CategoryEntity entity = categoryRepository.findOne(id);
		return categoryConverter.toDto(entity);
	}
	@Transactional
	public CategoryDTO save(CategoryDTO dto) {
		//CategoryEntity category = categoryRepository.findOneByCode(dto.getCategoryCode());
		CategoryEntity categoryEntity = new CategoryEntity();
		if(dto.getId() != null) {
//			System.out.println(dto.getId());
//			System.out.println(dto.getCreatedBy());
			CategoryEntity oldCategory = categoryRepository.findOne(dto.getId());
		//	oldNew.setCategory(category);
			categoryEntity = categoryConverter.toEntity(oldCategory, dto);
	        categoryEntity.setModifiedDate(new Timestamp(System.currentTimeMillis())); // Thay đổi thời gian hiện tại
	        categoryEntity.setModifiedBy(SecurityUtils.getPrincipal().getUsername()); // Lấy tên người dùng hiện tại
		}else {
			System.out.println("id: "+dto.getId());
			categoryEntity = categoryConverter.toEntity(dto);
			//newEntity.setCategory(category);
			
			 categoryEntity.setCreatedDate(new Timestamp(System.currentTimeMillis())); 
		     //MyUser user = new
			 categoryEntity.setCreateBy(SecurityUtils.getPrincipal().getUsername()); 
		}
		return categoryConverter.toDto(categoryRepository.save(categoryEntity));
	}
	
	@Transactional
	public void delete(long []ids) {
		for(long id: ids) {
		categoryRepository.delete(id);
		}
	}


}
