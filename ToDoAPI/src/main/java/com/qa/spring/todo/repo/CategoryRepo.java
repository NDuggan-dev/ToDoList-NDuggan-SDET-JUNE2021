package com.qa.spring.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.spring.todo.domain.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>{
	
}
