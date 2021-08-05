package com.qa.spring.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.qa.spring.todo.domain.Category;
import com.qa.spring.todo.domain.PriorityEnum;
import com.qa.spring.todo.domain.Task;
import com.qa.spring.todo.domain.builder.CategoryBuilder;
import com.qa.spring.todo.domain.builder.TaskBuilder;
import com.qa.spring.todo.repo.CategoryRepo;
import com.qa.spring.todo.repo.TaskRepo;

@SpringBootApplication
public class ToDoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(TaskRepo taskRepo, CategoryRepo categoryRepo) {
		return args -> {
			Category category1 = new CategoryBuilder()
					.name("Shopping")
					.colour("#ff3e05")
					.build();
			Task task1 = new TaskBuilder()
							.dueDate(LocalDate.of(2022, 1, 6))
							.title("ShoppingList")
							.description("Eggs, Bread")
							.priority(PriorityEnum.Medium)
							.category(category1)
							.build();
			Task task2 = new TaskBuilder()
					.dueDate(LocalDate.of(2022, 1, 6))
					.title("ShoppingList")
					.description("Eggs, Soup")
					.priority(PriorityEnum.Low)
					.category(category1)
					.build();
			

			categoryRepo.save(category1);
			taskRepo.save(task1);
			taskRepo.save(task2);
		};
	}
	

}
