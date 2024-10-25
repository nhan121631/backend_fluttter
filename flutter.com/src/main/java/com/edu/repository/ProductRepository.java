package com.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
	
	@Query(value = "SELECT * FROM product WHERE name like :param% OR (sell_price <= CAST(:param AS DECIMAL))", nativeQuery = true)
	List<ProductEntity> findProduct(@Param("param") String param);

}
