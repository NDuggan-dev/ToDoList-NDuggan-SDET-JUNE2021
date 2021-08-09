package com.qa.spring.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.spring.todo.domain.Task;
import com.qa.spring.todo.domain.builder.TaskBuilder;
import com.qa.spring.todo.exception.ResourceNotFoundException;
import com.qa.spring.todo.repo.CategoryRepo;
import com.qa.spring.todo.repo.TaskRepo;

@Service 
public class TaskService {
 
	private TaskRepo taskRepo; 
	private CategoryRepo categoryRepo;
	
	@Autowired
	public TaskService(TaskRepo taskRepo, CategoryRepo categoryRepo) {
		this.taskRepo = taskRepo;
		this.categoryRepo = categoryRepo;
	}
	
	public List<Task> getAll(){
		return this.taskRepo.findAll();
	}
	
	public List<Task> getAllByCategory(Long categoryId){
		return this.taskRepo.findByCategoryId(categoryId);
	}
	
	public Task create(Long categoryId, Task task) {
		return this.categoryRepo.findById(categoryId).map(category -> {
			task.setCategory(category);
			return this.taskRepo.saveAndFlush(task);
		}).orElseThrow(() -> new ResourceNotFoundException("CategoryId" + categoryId + " not found"));			
	}
	
	public Task update(Long categoryId, Long taskId, Task newTask) {
		if(!this.categoryRepo.existsById(categoryId)) {
			throw new ResourceNotFoundException("CategoryId" + categoryId + " not found");
		}
		return this.taskRepo.findById(taskId).map(updateTask -> {
			updateTask = new TaskBuilder()
					.id(taskId)
					.category(this.categoryRepo.getById(categoryId))
					.description(newTask.getDescription())
					.dueDate(newTask.getDueDate())
					.title(newTask.getTitle())
					.priority(newTask.getPriority())
					.build();
			return this.taskRepo.save(updateTask);
		}).orElseThrow(() -> new ResourceNotFoundException("TaskId" + taskId + " not found"));			
	}
	public Boolean deleteTask(Long id) {
		return this.taskRepo.findById(id).map(task -> {
			this.taskRepo.deleteById(id);
			return true;
		}).orElseThrow(() -> new ResourceNotFoundException("TaskId" + id + " not found"));
	}	
}
