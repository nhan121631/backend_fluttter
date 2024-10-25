package com.edu.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.entity.CartitemEntity;
import com.edu.repository.CartItemRepository;

@Service
public class CartItemService {
	@Autowired 
	private CartItemRepository cartItemRepository;
	
	public boolean cartitemExists(Long cartId, Long productId) {
	    return cartItemRepository.findByCartIdAndProductId(cartId, productId).isPresent();
	}
	
	public void save(CartitemEntity cartItem){
		cartItemRepository.save(cartItem);
	}
	
	@Transactional
	public void updateQuantity(Long cartId, Long productId) {
		cartItemRepository.updateQuantity(cartId, productId);
	}
	
	public int quantityCart(Long userId) {
		return cartItemRepository.quantityCart(userId);
	}

}
