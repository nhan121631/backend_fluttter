package com.edu.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edu.dto.ReviewDTO;
import com.edu.entity.ReviewEntity;

@Component
public class ReviewConverter {
	@Autowired
	private UserConverter userConverter;
	public ReviewDTO toDto(ReviewEntity entity) {
		ReviewDTO dto = new ReviewDTO();
		dto.setId(entity.getId());
		dto.setUser(userConverter.toDto(entity.getUser()));
		dto.setStar(entity.getStar());
		dto.setComment(entity.getComment());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setModifiedDate(entity.getModifiedDate());
		return dto;
	}
}
