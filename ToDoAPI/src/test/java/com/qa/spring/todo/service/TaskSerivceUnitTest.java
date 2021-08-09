package com.qa.spring.todo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.spring.todo.domain.Category;
import com.qa.spring.todo.domain.PriorityEnum;
import com.qa.spring.todo.domain.Task;
import com.qa.spring.todo.domain.builder.CategoryBuilder;
import com.qa.spring.todo.domain.builder.TaskBuilder;
import com.qa.spring.todo.repo.CategoryRepo;
import com.qa.spring.todo.repo.TaskRepo;


@SpringBootTest
public class TaskSerivceUnitTest {
	
	@MockBean
	private CategoryRepo categoryRepo;
	
	@MockBean
	private TaskRepo taskRepo;
	
	@Autowired
	private TaskService taskService;
	
	@Test
	void testCreateTask() {
		Long categoryId = 1L;
		Optional<Category> category= Optional.of(new CategoryBuilder().id(1L).name("Shopping").colour("#ff3e05").build());
		Task task = new TaskBuilder().dueDate(LocalDate.of(2022, 1, 6)).title("Tuesday").description("Eggs, Bread")
				.priority(PriorityEnum.Medium).build();

		Mockito.when(this.categoryRepo.findById(categoryId)).thenReturn(category);
		Mockito.when(this.taskRepo.saveAndFlush(task)).thenReturn(task);
		
		assertThat(this.taskService.create(categoryId, task)).isEqualTo(task);
		
		Mockito.verify(this.categoryRepo, Mockito.times(1)).findById(categoryId);	
		Mockito.verify(this.taskRepo, Mockito.times(1)).saveAndFlush(task);
	}
	
	@Test
	void testGetAllTasksByCategory() {
		Long categoryId = 1L;
		Task task1 = new TaskBuilder().dueDate(LocalDate.of(2022, 1, 6)).title("Tuesday").description("Eggs, Bread")
				.priority(PriorityEnum.Medium).build();
		List<Task> taskList = new ArrayList<>();
		taskList.add(task1);
		
		Mockito.when(this.taskRepo.findByCategoryId(categoryId)).thenReturn(taskList);
		
		assertThat(this.taskService.getAllByCategory(categoryId)).isEqualTo(taskList);
		
		Mockito.verify(this.taskRepo, Mockito.times(1)).findByCategoryId(categoryId);
	}
	
//	@Test
//	void testUpdateCategory() {
//		Long categoryId = 1L;
//		Long taskId = 1L;
//		Category category= new CategoryBuilder().id(1L).name("Shopping List").colour("#ff3e05").build();
//		Optional<Task> optionalTask = Optional.of(new TaskBuilder().id(1L).dueDate(LocalDate.of(2022, 1, 6)).title("Tuesday").description("Eggs, Bread")
//				.category(category)
//				.priority(PriorityEnum.Medium).build());
//		Task task = new TaskBuilder().dueDate(LocalDate.of(2022, 1, 6)).title("Tuesday").description("Eggs, Bread")
//				.category(category)
//				.priority(PriorityEnum.Medium).build();
//		Task updatedTask = new TaskBuilder().id(1L).dueDate(LocalDate.of(2022, 1, 6)).title("wednesday").description("Eggs, Bread")
//				.category(category)
//				.priority(PriorityEnum.Medium).build();
//		
//		Mockito.when(this.categoryRepo.existsById(categoryId)).thenReturn(true);
//		Mockito.when(this.taskRepo.findById(taskId)).thenReturn(optionalTask);
//		Mockito.when(this.categoryRepo.getById(categoryId)).thenReturn(category);
//		Mockito.when(this.taskRepo.save(updatedTask)).thenReturn(updatedTask);
//
//		
//		assertThat(this.taskService.update(categoryId, taskId, task)).isEqualTo(updatedTask);
//		
//		Mockito.verify(this.categoryRepo, Mockito.times(1)).existsById(categoryId);
//		Mockito.verify(this.taskRepo, Mockito.times(1)).findById(taskId);
//		Mockito.verify(this.categoryRepo, Mockito.times(1)).getById(categoryId);
//		Mockito.verify(this.taskRepo, Mockito.times(1)).save(updatedTask);
//	}
	
	@Test 
	void testDeleteCategory() {
		Category category= new CategoryBuilder().id(1L).name("Shopping List").colour("#ff3e05").build();
		Optional<Task> optionalTask = Optional.of(new TaskBuilder().id(1L).dueDate(LocalDate.of(2022, 1, 6)).title("Tuesday").description("Eggs, Bread")
				.category(category)
				.priority(PriorityEnum.Medium).build());
		Long id = 1L;
		
		Mockito.when(this.taskRepo.findById(id)).thenReturn(optionalTask);
		
		assertThat(this.taskService.deleteTask(id)).isEqualTo(true);
		
		Mockito.verify(this.taskRepo, Mockito.times(1)).findById(id);	
	}
	

}
