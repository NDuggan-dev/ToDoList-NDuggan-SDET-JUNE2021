package com.qa.spring.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qa.spring.todo.domain.Category;
import com.qa.spring.todo.domain.builder.CategoryBuilder;
import com.qa.spring.todo.exception.ResourceNotFoundException;
import com.qa.spring.todo.repo.CategoryRepo;

@Service
public class CategoryService {

	private CategoryRepo repo; 
	
	@Autowired
	public CategoryService(CategoryRepo repo) {
		this.repo = repo;
	}
	
	public Category createCategory(Category categoryData) {
		return this.repo.saveAndFlush(categoryData);
	}

	public List<Category> getAll(){
		return this.repo.findAll();
	}
	
	public Category updateCategory(Category categoryData, Long id) {
		return this.repo.findById(id).map(category -> {
			 category = new CategoryBuilder()
					 		.id(id)
					 		.name(categoryData.getName())
					 		.colour(categoryData.getColour())
					 		.build();
            return this.repo.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + id + " not found"));
	}
	
	public Boolean deleteCategory(Long id) {
		return this.repo.findById(id).map(category -> {
			this.repo.deleteById(id);
			return true;
		}).orElseThrow(() -> new ResourceNotFoundException("CategoryId" + id + " not found"));
	}

	public Category getById(Long id) {
		
		return this.repo.findById(id).map(category -> {
			return this.repo.getById(id);
		})
				.orElseThrow(() -> new ResourceNotFoundException("CategoryId" + id + "not found"));
	}
}
