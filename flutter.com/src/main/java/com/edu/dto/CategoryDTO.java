package com.edu.dto;

import java.util.List;

public class CategoryDTO extends AbstractDTO<CategoryDTO>{

	private Long id;
	private String name;
	private List<ProductDTO> news;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductDTO> getNews() {
		return news;
	}

	public void setNews(List<ProductDTO> news) {
		this.news = news;
	}
	
	
}
