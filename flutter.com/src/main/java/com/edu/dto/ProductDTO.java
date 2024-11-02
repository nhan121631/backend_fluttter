package com.edu.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO extends AbstractDTO<ProductDTO>{
	private Long id;
	private String name;
	private double costPrice;
	private double sellPrice;
	private String thumbnail;
	private int quantity;
	private String description;
	private Long categoryId;
	private List<ReviewDTO> reviews = new ArrayList<>();
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
	public double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(double cotsPrice) {
		this.costPrice = cotsPrice;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public List<ReviewDTO> getReviews() {
		return reviews;
	}
	public void setReviews(List<ReviewDTO> reviews) {
		this.reviews = reviews;
	}
	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", cotsPrice=" + costPrice + ", sellPrice=" + sellPrice
				+ ", thumbnail=" + thumbnail + ", quantity=" + quantity + ", description=" + description
				+ ", categoryId=" + categoryId + "]";
	}
	
	
	
}
