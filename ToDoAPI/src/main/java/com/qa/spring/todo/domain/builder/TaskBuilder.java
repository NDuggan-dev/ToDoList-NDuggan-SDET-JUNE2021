package com.qa.spring.todo.domain.builder;




import java.time.LocalDate;

import com.qa.spring.todo.domain.Category;
import com.qa.spring.todo.domain.PriorityEnum;
import com.qa.spring.todo.domain.Task;

public class TaskBuilder {

	
	private Long id;
	private String title; 
	private String description;
	private LocalDate dueDate;
	private PriorityEnum priority;
	private Category category;
	
	public TaskBuilder() {}
	
	public Task build() {
		return new Task(id, title, description, dueDate, priority, category);
	}

	public TaskBuilder id(Long id) {
		this.id = id;
	return this;
	}

	public TaskBuilder title(String title) {
		this.title = title;
		return this;
	}

	public TaskBuilder description(String description) {
		this.description = description;
		return this;
	}

	public TaskBuilder dueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
		return this;
	}

	public TaskBuilder priority(PriorityEnum priority) {
		this.priority = priority;
		return this;
	}
	
	public TaskBuilder category(Category category) {
		this.category = category;
		return this;
	}
	
}
