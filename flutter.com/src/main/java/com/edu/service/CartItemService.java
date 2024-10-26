package com.edu.service;

import java.util.List;

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
	
	@Transactional
	public synchronized  void save(CartitemEntity cartItem){
		cartItemRepository.save(cartItem);
	}
	
	@Transactional
	public void updateQuantity(Long cartId, Long productId) {
		cartItemRepository.updateQuantity(cartId, productId);
	}
	
	public int quantityCart(Long userId) {
		return cartItemRepository.quantityCart(userId);
	}
	
	public List<CartitemEntity> findByCartId(Long id){
		return cartItemRepository.findByCartId(id);
	}
	
	@Transactional
	public void delete(Long id) {
		cartItemRepository.delete(id);
	}
	@Transactional
	public void updateQuantityItem(Long id, int state) {
		cartItemRepository.updateQuantityItem(id, state);
	}
	@Transactional
	public void deleteByCartId(Long cart_id) {
		cartItemRepository.deleteByCartId(cart_id);
	}

}
