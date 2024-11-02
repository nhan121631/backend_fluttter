package com.edu.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edu.dto.ProductDTO;
import com.edu.dto.ReviewDTO;
import com.edu.entity.ProductEntity;
import com.edu.entity.ReviewEntity;

@Component
public class ProductConverter {
	@Autowired
	private ReviewConverter reviewConverter;

	public ProductDTO toDto (ProductEntity entity) {
		ProductDTO result = new ProductDTO();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setCostPrice(entity.getCostPrice());
		result.setSellPrice(entity.getSellPrice());
		result.setQuantity(entity.getQuantity());
		result.setThumbnail(entity.getThumbnail());
		result.setCategoryId(entity.getCategory().getId());
		
		List<ReviewDTO> list = new ArrayList<>();
		for (ReviewEntity item : entity.getReviews()) {
			list.add(reviewConverter.toDto(item));
		}
		result.setReviews(list);
		return result;
	}
	
	public ProductEntity toEntity(ProductDTO dto) {
	    ProductEntity result = new ProductEntity();
	    result.setId(dto.getId()); // Nếu có trường ID trong DTO
	    result.setName(dto.getName()); // Đặt tên từ DTO
	    result.setDescription(dto.getDescription()); // Đặt mô tả từ DTO
	    result.setCostPrice(dto.getCostPrice()); // Đặt giá nhập từ DTO
	    result.setSellPrice(dto.getSellPrice()); // Đặt giá bán từ DTO
	    result.setQuantity(dto.getQuantity()); // Đặt số lượng từ DTO
	    result.setThumbnail(dto.getThumbnail()); // Đặt hình ảnh từ DTO
	    return result;
	}

//	public ProductEntity toEntity (ProductDTO dto) {
//		ProductEntity result = new ProductEntity();
//		result.setTitle(dto.getTitle());
//		result.setShortDescription(dto.getShortDescription());
//		result.setAuthor(dto.getAuthor());
//		result.setThumbnail(dto.getThumbnail());
//		result.setStatus(dto.getStatus());
//      result.setCategoryCode(dto.getCategory().getCode());
//		return result;
//	}
	public ProductEntity toEntity(ProductEntity result, ProductDTO dto) {
	    result.setName(dto.getName()); // Cập nhật tên sản phẩm
	    result.setDescription(dto.getDescription()); // Cập nhật mô tả đầy đủ
	    result.setCostPrice(dto.getCostPrice()); // Cập nhật giá nhập
	    result.setSellPrice(dto.getSellPrice()); // Cập nhật giá bán
	    result.setQuantity(dto.getQuantity()); // Cập nhật số lượng
	    result.setThumbnail(dto.getThumbnail()); // Cập nhật hình ảnh
	    return result; // Trả về đối tượng đã được cập nhật
	}

//	public ProductEntity toEntity (ProductEntity result, ProductDTO dto) {
//		result.setName(dto.getName());
//		result.setShortDescription(dto.getShortDescription());
////		result.setContent(dto.getContent());
////		result.setThumnail(dto.getThumbnail());
////sult.setCategoryCode(dto.getCategory().getCode());
//		return result;
//	}
}
