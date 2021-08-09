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

//	@Bean
//	CommandLineRunner commandLineRunner(TaskRepo taskRepo, CategoryRepo categoryRepo) {
//		return args -> {
//			Category category1 = new CategoryBuilder().name("Shopping").colour("#ff3e05").build();
//			Category category2 = new CategoryBuilder().name("School").colour("#ecca05").build();
//			Category category3 = new CategoryBuilder().name("one").colour("#ff3e05").build();
//			Category category4 = new CategoryBuilder().name("new").colour("#cc6fcc").build();
//			Task task1 = new TaskBuilder().dueDate(LocalDate.of(2022, 1, 6)).title("Tuesday").description("Eggs, Bread")
//					.priority(PriorityEnum.Medium).category(category1).build();
//			Task task2 = new TaskBuilder().dueDate(LocalDate.of(2022, 1, 6)).title("Monday").description("Eggs, Soup")
//					.priority(PriorityEnum.Low).category(category1).build();
//			Task task3 = new TaskBuilder().dueDate(LocalDate.of(2022, 1, 6)).title("Monday").description("Eggs, Soup")
//					.priority(PriorityEnum.Low).category(category2).build();
//			Task task4 = new TaskBuilder().dueDate(LocalDate.of(2022, 1, 6)).title("Tuesday").description("Eggs, Bread")
//					.priority(PriorityEnum.Medium).category(category3).build();
//			Task task5 = new TaskBuilder().dueDate(LocalDate.of(2022, 1, 6)).title("Monday").description("Eggs, Soup")
//					.priority(PriorityEnum.Low).category(category4).build();
//			Task task6 = new TaskBuilder().dueDate(LocalDate.of(2022, 1, 6)).title("Monday").description("Eggs, Soup")
//					.priority(PriorityEnum.Low).category(category4).build();
//
//			categoryRepo.save(category1);
//			categoryRepo.save(category2);
//			taskRepo.save(task1);
//			taskRepo.save(task2);
//			taskRepo.save(task3);
//			categoryRepo.save(category3);
//			categoryRepo.save(category4);
//			taskRepo.save(task4);
//			taskRepo.save(task5);
//			taskRepo.save(task6);
//		};
//	}

}
