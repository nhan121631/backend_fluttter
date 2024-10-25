package com.edu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long>{

	UserEntity findOneByUserNameAndStatus(String name, int status);
    Optional<UserEntity> findByUserName(String username);
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);
    
    @Query(value = "select * from user where username =:username", nativeQuery = true)
    UserEntity findUserByUname(@Param("username") String username);


}
