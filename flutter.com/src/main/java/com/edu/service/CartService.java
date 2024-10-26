package com.edu.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.entity.CartEntity;
import com.edu.repository.CartRepository;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;
	
	public boolean cartExists(Long id) {
	    return cartRepository.findByUserId(id).isPresent();
	}
	
	@Transactional
	public void save(CartEntity cart) {
		cartRepository.save(cart);
	}
	public CartEntity getOneCart(Long userId) {
		Optional<CartEntity> cartOptional = cartRepository.findByUserId(userId);
		CartEntity cart = cartOptional.get();
		return cart;
	}
	
	public void delete(Long id) {
		cartRepository.delete(id);
	}

}
