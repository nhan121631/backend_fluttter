package com.edu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{
	Optional<ReviewEntity> findByUserIdAndProductId(Long user_id, Long product_id);
	ReviewEntity findOneByUserIdAndProductId(Long user_id, Long product_id);

}
