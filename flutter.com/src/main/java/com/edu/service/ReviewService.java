package com.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.entity.ReviewEntity;
import com.edu.repository.ReviewRepository;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;
	
	public boolean reviewExists(Long user_id, Long product_id) {
	    return reviewRepository.findByUserIdAndProductId(user_id, product_id).isPresent();
	}
	
	public ReviewEntity findOneByUserIdAndProductId(Long user_id, Long product_id) {
		return reviewRepository.findOneByUserIdAndProductId(user_id, product_id);
	}
	
	public void save(ReviewEntity review) {
		 reviewRepository.save(review);
	}
}
