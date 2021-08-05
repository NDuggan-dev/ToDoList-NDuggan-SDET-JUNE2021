package com.qa.spring.todo.domain.builder;




import java.time.LocalDate;
import java.util.List;

import com.qa.spring.todo.domain.Category;
import com.qa.spring.todo.domain.PriorityEnum;
import com.qa.spring.todo.domain.Task;

public class CategoryBuilder {

	private Long id;
	private String name; 
	private String colour;

	
	public CategoryBuilder() {}
	
	public Category build() {
		return new Category(id, name, colour);
	}

	public CategoryBuilder id(Long id) {
		this.id = id;
	return this;
	}

	public CategoryBuilder name(String name) {
		this.name = name;
		return this;
	}

	public CategoryBuilder colour(String colour) {
		this.colour = colour;
		return this;
	}


	
}
