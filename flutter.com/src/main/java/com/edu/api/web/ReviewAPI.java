package com.edu.api.web;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.entity.ProductEntity;
import com.edu.entity.ReviewEntity;
import com.edu.entity.UserEntity;
import com.edu.service.ReviewService;

@RestController(value = "reviewAPIOfWeb")
public class ReviewAPI {
	@Autowired 
	private ReviewService reviewService;

	@PostMapping(value = "/api/review/add", consumes = "application/x-www-form-urlencoded; charset=UTF-8", produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> addReview(
			@RequestParam("user_id") Long user_id, 
			@RequestParam("product_id")Long product_id,
			@RequestParam("star") int star,
			@RequestParam("comment") String comment){
		if(reviewService.reviewExists(user_id, product_id)) {
			ReviewEntity review = reviewService.findOneByUserIdAndProductId(user_id, product_id);
			review.setComment(comment);
			review.setStar(star);
			review.setModifiedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
			reviewService.save(review);
			return ResponseEntity.ok("Cập nhật đánh giá thành công");

		}
		ReviewEntity review = new ReviewEntity();
		review.setComment(comment);
		review.setStar(star);
		ProductEntity p = new ProductEntity();
		p.setId(product_id);
		review.setProduct(p);
		UserEntity u = new UserEntity();
		u.setId(user_id);
		review.setUser(u);
		review.setCreatedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		reviewService.save(review);
		
		return ResponseEntity.ok("Thêm review thành công");
	}
}
