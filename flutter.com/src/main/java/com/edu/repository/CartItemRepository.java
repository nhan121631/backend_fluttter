package com.edu.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.entity.CartitemEntity;

public interface CartItemRepository extends JpaRepository<CartitemEntity, Long> {

    Optional<CartitemEntity> findByCartIdAndProductId(Long cartId, Long productId);

    @Modifying
    @Transactional
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

    @Modifying
    @Query(value = "UPDATE cartitem SET quantity = quantity + :state WHERE id = :id", nativeQuery = true)
    void updateQuantityItem(@Param("id") Long id, @Param("state") int state);
    
    void deleteByCartId(Long cart_id);
//    
//    @Query(value = "SELECT cartitem.*\r\n"
//    		+ "FROM cartitem\r\n"
//    		+ "INNER JOIN cart ON cartitem.cart_id = cart.id\r\n"
//    		+ "INNER JOIN user ON cart.user_id = user.id\r\n"
//    		+ "WHERE user.id = :user_id\r\n"
//    		+ "")
}
