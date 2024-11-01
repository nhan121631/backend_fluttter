package com.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
	
	@Query(value = "SELECT * FROM product WHERE name like :param% OR (sell_price <= CAST(:param AS DECIMAL))", nativeQuery = true)
	List<ProductEntity> findProduct(@Param("param") String param);
	
	@Query(value = "SELECT product.*, SUM(orderitem.quantity) AS total_sold\r\n"
			+ "FROM product\r\n"
			+ "INNER JOIN orderitem ON product.id = orderitem.product_id\r\n"
			+ "INNER JOIN orders ON orderitem.order_id = orders.id\r\n"
			+ "WHERE orders.status <> 1\r\n"
			+ "GROUP BY product.id\r\n"
			+ "ORDER BY total_sold desc limit 20", nativeQuery = true)
	List<ProductEntity> getProductPopular();
	
	@Query(value = "SELECT * \r\n"
			+ "FROM product \r\n"
			+ "WHERE product.sell_price BETWEEN :sell1 AND :sell2\r\n"
			+ "", nativeQuery = true)
	List<ProductEntity> getFilterProduct(@Param("sell1") double sell1, 
			@Param("sell2") double sell2);
	
}
