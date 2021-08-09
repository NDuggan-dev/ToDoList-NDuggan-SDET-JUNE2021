package com.qa.spring.todo.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;





public class TaskTest {
		@Test 
		public void testConstructorOne() {
			Category category = new Category(1L, "Shopping", "#FFF");
			Task task = new Task(1L, "Tuesday", "Eggs", LocalDate.of(2022, 1, 6), PriorityEnum.Low , category);
			assertTrue(task instanceof Task);
		}
		
		@Test 
		public void testConstructorTwo() {
			Task task = new Task();
			assertTrue(task instanceof Task);
		}
		
		@Test 
		public void testGetters() {
			Category category = new Category(1L, "Shopping", "#FFF");
			Task task = new Task(1L, "Tuesday", "Eggs", LocalDate.of(2022, 1, 6), PriorityEnum.Low , category);
			Long expectedId = 1L;
			String expectedName = "Tuesday";
			String expectedDescription = "Eggs";
			LocalDate expectedDate = LocalDate.of(2022, 1, 6);
			PriorityEnum expectedPriority = PriorityEnum.Low;
			
			assertEquals(expectedId, task.getId());
			assertEquals(expectedName, task.getTitle());
			assertEquals(expectedDescription, task.getDescription());
			assertEquals(expectedDate, task.getDueDate());
			assertEquals(expectedPriority, task.getPriority());
			assertEquals(category, task.getCategory());
		}
		
		@Test
		public void testSetters() {
			Category category = new Category(1L, "Shopping", "#FFF");
			
			Task task = new Task(1L, "Tuesday", "Eggs", LocalDate.of(2022, 1, 6), PriorityEnum.Low , category);
			Long expectedId = 2L;
			task.setId(expectedId);
			String expectedName = "Monday";
			task.setTitle(expectedName);
			String expectedDescription = "Bread";
			task.setDescription(expectedDescription);
			LocalDate expectedDate = LocalDate.of(2021, 1, 6);
			task.setDueDate(expectedDate);
			PriorityEnum expectedPriority = PriorityEnum.Medium;
			task.setPriority(expectedPriority);
			Category expectedCategory = new Category(2L, "Shopping Lists", "#FFF");
			task.setCategory(expectedCategory);
		
			
			assertEquals(expectedId, task.getId());
			assertEquals(expectedName, task.getTitle());
			assertEquals(expectedDescription, task.getDescription());
			assertEquals(expectedDate, task.getDueDate());
			assertEquals(expectedPriority, task.getPriority());
			assertEquals(expectedCategory, task.getCategory());
		}
		
		@Test 
		public void testToString() {
			Category category = new Category(1L, "Shopping", "#FFF");
			Task task = new Task(1L, "Tuesday", "Eggs", LocalDate.of(2022, 1, 6), PriorityEnum.Low , category);
			String expected = "Task [id=" + task.getId() + ", title=" + task.getTitle() + ", description=" + task.getDescription() + ", dueDate=" + task.getDueDate()
					+ ", priority=" + task.getPriority() + ", category=" + task.getCategory() + "]";
			assertEquals(expected, task.toString());
		}
	
		
		@Test
		public void testEqualsAndHashCode() {
			Category category = new Category(1L, "Shopping", "#FFF");
			Task task = new Task(1L, "Tuesday", "Eggs", LocalDate.of(2022, 1, 6), PriorityEnum.Low , category);
			Task task2 = new Task(1L, "Tuesday", "Eggs", LocalDate.of(2022, 1, 6), PriorityEnum.Low , category);
			assertTrue(task.equals(task2) && task2.equals(task));
			assertTrue(task.hashCode() == task2.hashCode());
		}
		
		@Test 
		public void testNullHashCodeEquals() {
			Category category = new Category(1L, "Shopping", "#FFF");
			Task task = new Task(null, null, null, null, null , null);
			Task task2 = new Task(1L, "Tuesday", "Eggs", LocalDate.of(2022, 1, 6), PriorityEnum.Low , category);
			assertFalse(task.equals(task2) && task2.equals(task));
			assertFalse(task.hashCode() == task2.hashCode());
			
		}
}

