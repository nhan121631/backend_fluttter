package com.edu.converter;

import org.springframework.stereotype.Component;

import com.edu.dto.UserDTO;
import com.edu.entity.UserEntity;

@Component
public class UserConverter {
	
	public UserDTO toDto(UserEntity entity) {
		UserDTO dto = new UserDTO();
		dto.setId(entity.getId());
		dto.setFullName(entity.getFullName());
		return dto;
	}
}
