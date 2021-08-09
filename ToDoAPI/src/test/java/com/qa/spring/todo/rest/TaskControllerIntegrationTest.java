package com.qa.spring.todo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.spring.todo.domain.Category;
import com.qa.spring.todo.domain.PriorityEnum;
import com.qa.spring.todo.domain.Task;
import com.qa.spring.todo.domain.builder.CategoryBuilder;
import com.qa.spring.todo.domain.builder.TaskBuilder;
import com.qa.spring.todo.exception.ResourceNotFoundException;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {
	

		// Mock Controller and relevant mappers
		@Autowired
		private MockMvc mock; 
		
		@Autowired
		private ObjectMapper mapper; // CONVERT REQUESTS TO JSON
		
		@Test
		void testTask() throws Exception {
			Category category = new CategoryBuilder().name("Shopping").colour("#ff3e05").build();
			String categoryAsJSON =  this.mapper.writeValueAsString(category);
			
			RequestBuilder mockRequest2 = 
					post("/category")
					.contentType(MediaType.APPLICATION_JSON)
					.content(categoryAsJSON);
			
			this.mock.perform(mockRequest2);
			
			//create
			Task task = new TaskBuilder().dueDate(LocalDate.of(2022, 1, 6)).title("Tuesday").description("Eggs, Bread")
					.priority(PriorityEnum.Medium).build();
			
			String taskAsJSON = this.mapper.writeValueAsString(task);
			
			RequestBuilder mockRequest = 
								post("/task/1/create")
								.contentType(MediaType.APPLICATION_JSON)
								.content(taskAsJSON);
			
			Task savedTask = new TaskBuilder().id(1L).dueDate(LocalDate.of(2022, 1, 6)).title("Tuesday").description("Eggs, Bread")
					.priority(PriorityEnum.Medium).build();
			
			String savedTaskAsJSON = this.mapper.writeValueAsString(savedTask);
			
			ResultMatcher matchStatus = status().isCreated(); 
			
			ResultMatcher matchBodyPost = content().json(savedTaskAsJSON);
			
			this.mock.perform(mockRequest).andExpect(matchBodyPost).andExpect(matchStatus);
			
			//getallbycategory
			List<Task> taskList = new ArrayList<>();
			taskList.add(savedTask);
			
			String savedTaskListAsJSON = this.mapper.writeValueAsString(taskList);
			
			RequestBuilder mockGetByCategoryRequest = get("/task/1/getallbycategory");
			
			ResultMatcher matchStatusGet = status().isOk();
			
			ResultMatcher matchBodyGet = content().json(savedTaskListAsJSON);
			
			this.mock.perform(mockGetByCategoryRequest).andExpect(matchStatusGet).andExpect(matchBodyGet);
			
			//getall
			RequestBuilder mockGetAllRequest = get("/task/getall");
			
			this.mock.perform(mockGetAllRequest).andExpect(matchStatusGet).andExpect(matchBodyGet);
			
			//put
			Task taskUpdated = new TaskBuilder().id(1L).dueDate(LocalDate.of(2022, 2, 6)).title("Wednesday").description("Eggs, Water")
					.priority(PriorityEnum.Low).build();
			
			String updateTaskAsJSON = this.mapper.writeValueAsString(taskUpdated);
			
			RequestBuilder mockPutRequest = put("/task/1/1/update")
					.contentType(MediaType.APPLICATION_JSON).content(updateTaskAsJSON);
			
			ResultMatcher matchStatusPut = status().isCreated();
			
			ResultMatcher matchBodyPut = content().json(updateTaskAsJSON);
			
			this.mock.perform(mockPutRequest).andExpect(matchBodyPut).andExpect(matchStatusPut);
			
			//delete
			Long taskId = 1L;
			
			String idAsJSON = this.mapper.writeValueAsString(taskId);
			
			RequestBuilder mockDeleteRequest = delete("/task/1")
					.contentType(MediaType.APPLICATION_JSON).content(idAsJSON);
			
			ResultMatcher matchBodyDelete = content().json(idAsJSON);
			
			ResultMatcher matchStatusDelete = status().isOk();
			
			this.mock.perform(mockDeleteRequest).andExpect(matchBodyDelete).andExpect(matchStatusDelete);
			
		}
		
		
		@Test 
		void testTaskDeleteFail() throws Exception {
			Long taskId = 2L;
			
			String idAsJSON = this.mapper.writeValueAsString(taskId);
			
			RequestBuilder mockDeleteRequest = delete("/task/2")
					.contentType(MediaType.APPLICATION_JSON).content(idAsJSON);
			
			ResultMatcher matchStatusDelete = status().isNotFound();
			
			this.mock.perform(mockDeleteRequest).andExpect(matchStatusDelete);
		}


		@Test 
		void testTaskUpdateFail() throws Exception {
			//put
			Task taskUpdated = new TaskBuilder().id(1L).dueDate(LocalDate.of(2022, 2, 6)).title("Wednesday").description("Eggs, Water")
					.priority(PriorityEnum.Low).build();
			
			String updateTaskAsJSON = this.mapper.writeValueAsString(taskUpdated);
			
			RequestBuilder mockPutRequest = put("/task/2/1/update")
					.contentType(MediaType.APPLICATION_JSON).content(updateTaskAsJSON);
			
			ResultMatcher matchStatusPut = status().isNotFound();
			
			
			this.mock.perform(mockPutRequest).andExpect(matchStatusPut).
			andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(result -> assertEquals("CategoryId2 not found", result.getResolvedException().getMessage()));
		}
		
		
}
