package com.edu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.converter.ProductConverter;
import com.edu.dto.ProductDTO;
import com.edu.entity.CategoryEntity;
import com.edu.entity.ProductEntity;
import com.edu.repository.CategoryRepository;
import com.edu.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductConverter productConverter;

	
	public List<ProductDTO> findAll(Pageable pageable) {
		List<ProductDTO> models = new ArrayList<>();
		List<ProductEntity> entities = productRepository.findAll(pageable).getContent();
		for (ProductEntity item: entities) {
			ProductDTO newDTO = productConverter.toDto(item);
			models.add(newDTO);
		}
		return models;
	}

	public List<ProductDTO> getALL() {
		List<ProductDTO> models = new ArrayList<>();
		//List<ProductEntity> entities = productRepository.findAll();
		List<ProductEntity> entities = productRepository.getProductPopular();
	    System.out.println("Số lượng sản phẩm trong cơ sở dữ liệu: " + entities.size()); // Log số lượng sản phẩm

		for (ProductEntity item: entities) {
			ProductDTO newDTO = productConverter.toDto(item);
			models.add(newDTO);
		}
		return models;
	}
	

	public List<ProductDTO> getFilterProduct(double sell1, double sell2) {
		List<ProductDTO> models = new ArrayList<>();
		List<ProductEntity> entities = productRepository.getFilterProduct(sell1, sell2);
	    System.out.println("Số lượng sản phẩm trong cơ sở dữ liệu: " + entities.size()); 

		for (ProductEntity item: entities) {
			ProductDTO newDTO = productConverter.toDto(item);
			models.add(newDTO);
		}
		return models;
	}
	
	public int getTotalItem() {
		return (int) productRepository.count();
	}

	public ProductDTO findById(long id) {
		ProductEntity entity = productRepository.findOne(id);
		return productConverter.toDto(entity);
	}
	
	public List<ProductEntity> findProduct(String param){
		List<ProductEntity> lists = productRepository.findProduct(param);
		return lists;
	}
	
	
	public ProductDTO save(ProductDTO dto) {
		CategoryEntity category = categoryRepository.findOne(dto.getCategoryId());
		ProductEntity newEntity = new ProductEntity();
		if(dto.getId() !=null && !dto.getId().equals("")) {
			ProductEntity oldNew = productRepository.findOne(dto.getId());
			oldNew.setCategory(category);
			newEntity = productConverter.toEntity(oldNew, dto);
		}else {
			newEntity = productConverter.toEntity(dto);
			newEntity.setCategory(category);
		}
		return productConverter.toDto(productRepository.save(newEntity));	
	}
	
	public void saveQ(ProductEntity product) {
			productRepository.save(product);
	}
	
	@Transactional
	public void delete(long []ids) {
		for(long id: ids) {
		productRepository.delete(id);
		}
	}
}
