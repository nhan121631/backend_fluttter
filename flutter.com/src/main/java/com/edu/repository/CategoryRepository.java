package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long>{

}
