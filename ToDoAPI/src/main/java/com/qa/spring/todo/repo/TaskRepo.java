package com.qa.spring.todo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.spring.todo.domain.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long>{

	List<Task> findByCategoryId(Long categoryId);
	Optional<Task> findByIdAndCategoryId(Long id, Long categoryId);
}
