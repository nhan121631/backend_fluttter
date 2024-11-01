package com.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
 
	List<OrderEntity> findByUserIdAndStatusIsNot(Long id, int tatus);
	
	@Modifying
	@Query(value = "UPDATE orders \r\n"
			+ "SET status = CASE \r\n"
			+ "                WHEN status < 2 THEN status + 1 \r\n"
			+ "                ELSE status \r\n"
			+ "             END \r\n"
			+ "WHERE id = :id", nativeQuery = true)
	void updateStatus(@Param("id") Long id);


}
