package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.OrderEntity;
import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
 

}
