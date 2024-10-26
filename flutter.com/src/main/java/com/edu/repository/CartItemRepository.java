package com.edu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.entity.CartitemEntity;

public interface CartItemRepository extends JpaRepository<CartitemEntity, Long>{

	Optional<CartitemEntity> findByCartIdAndProductId(Long cartId, Long productId);
	
	@Modifying
	@Query(value = "UPDATE cartitem SET quantity = quantity + 1 WHERE cart_id = :cartId AND product_id = :productId", nativeQuery = true)
	void updateQuantity(@Param("cartId") Long cartId, @Param("productId") Long productId);

	@Query(value = "SELECT COALESCE(SUM(ci.quantity), 0) " +
            "FROM cartitem ci " +
            "INNER JOIN cart c ON ci.cart_id = c.id " +
            "INNER JOIN user u ON c.user_id = u.id " +
            "WHERE u.id = :userId", nativeQuery = true)
	int quantityCart(@Param("userId") Long userId);
	
	@EntityGraph(attributePaths = {"product"})
	List<CartitemEntity> findByCartId(Long id);

	
}
