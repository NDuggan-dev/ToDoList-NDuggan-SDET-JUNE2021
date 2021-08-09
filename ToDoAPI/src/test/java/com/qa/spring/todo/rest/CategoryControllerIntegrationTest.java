package com.qa.spring.todo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.spring.todo.domain.Category;
import com.qa.spring.todo.domain.builder.CategoryBuilder;
import com.qa.spring.todo.exception.ResourceNotFoundException;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest {
	

		// Mock Controller and relevant mappers
		
		@Autowired
		private MockMvc mock; 
		
		@Autowired
		private ObjectMapper mapper; // CONVERT REQUESTS TO JSON
		
		
		@Test
		void testCategory() throws Exception {
			
			//create
			Category category = new CategoryBuilder().name("Shopping").colour("#ff3e05").build();
			
			String categoryAsJSON = this.mapper.writeValueAsString(category);
			
			RequestBuilder mockRequest = 
								post("/category")
								.contentType(MediaType.APPLICATION_JSON)
								.content(categoryAsJSON);
			
			Category savedCategory = new CategoryBuilder().id(1L).name("Shopping").colour("#ff3e05").build();
			
			String savedCategoryAsJSON = this.mapper.writeValueAsString(savedCategory);
			
			ResultMatcher matchStatus = status().isCreated(); 
			
			ResultMatcher matchBodyPost = content().json(savedCategoryAsJSON);
			
			this.mock.perform(mockRequest).andExpect(matchBodyPost).andExpect(matchStatus);
			
			//get
			RequestBuilder mockGetRequest = get("/category/1");
			
			ResultMatcher matchStatusGet = status().isOk();
			
			ResultMatcher matchBodyGet = content().json(savedCategoryAsJSON);
			
			this.mock.perform(mockGetRequest).andExpect(matchStatusGet).andExpect(matchBodyGet);
			
			//getall
			RequestBuilder mockGetAllRequest = get("/category/all");
			
			List<Category> categoryList = new ArrayList<>();
			categoryList.add(savedCategory);
			
			String categoryListAsJSON = this.mapper.writeValueAsString(categoryList);
			
			ResultMatcher matchStatusGetAll = status().isOk();
			
			ResultMatcher matchBodyGetAll = content().json(categoryListAsJSON);
			
			this.mock.perform(mockGetAllRequest).andExpect(matchBodyGetAll).andExpect(matchStatusGetAll);
			
			//put
			Category categoryUpdated = new CategoryBuilder().id(1L).name("Work").colour("#ff3e05").build();
			
			String updateCategoryAsJSON = this.mapper.writeValueAsString(categoryUpdated);
			
			RequestBuilder mockPutRequest = put("/category/1")
					.contentType(MediaType.APPLICATION_JSON).content(updateCategoryAsJSON);
			
			ResultMatcher matchStatusPut = status().isOk();
			
			ResultMatcher matchBodyPut = content().json(updateCategoryAsJSON);
			
			this.mock.perform(mockPutRequest).andExpect(matchBodyPut).andExpect(matchStatusPut);
			
			//delete
			Long categoryId = 1L;
			
			String idAsJSON = this.mapper.writeValueAsString(categoryId);
			
			RequestBuilder mockDeleteRequest = delete("/category/1")
					.contentType(MediaType.APPLICATION_JSON).content(idAsJSON);
			
			ResultMatcher matchBodyDelete = content().json(idAsJSON);
			
			ResultMatcher matchStatusDelete = status().isOk();
			
			this.mock.perform(mockDeleteRequest).andExpect(matchBodyDelete).andExpect(matchStatusDelete);
			
		}
		
		@Test 
		void testCategoryPutFail() throws Exception {
			Category categoryUpdated = new CategoryBuilder().id(1L).colour("#ff3e05").build();
			
			String updateCategoryAsJSON = this.mapper.writeValueAsString(categoryUpdated);
			
			RequestBuilder mockPutRequest = put("/category/1")
					.contentType(MediaType.APPLICATION_JSON).content(updateCategoryAsJSON);
			
			ResultMatcher matchStatusPut = status().isNoContent();
		
			this.mock.perform(mockPutRequest).andExpect(matchStatusPut);
			
		}
		
		@Test 
		void testCategoryDeleteFail() throws Exception {
			Long categoryId = 1L;
			
			String idAsJSON = this.mapper.writeValueAsString(categoryId);
			
			RequestBuilder mockDeleteRequest = delete("/category/1")
					.contentType(MediaType.APPLICATION_JSON).content(idAsJSON);
			
			ResultMatcher matchStatusDelete = status().isNotFound();
			
			this.mock.perform(mockDeleteRequest).andExpect(matchStatusDelete);
		}


		@Test 
		void testCategoryUpdateFail() throws Exception {
			//put
			Category categoryUpdated = new CategoryBuilder().name("Work").colour("#ff3e05").build();
			
			String updateCategoryAsJSON = this.mapper.writeValueAsString(categoryUpdated);
			
			RequestBuilder mockPutRequest = put("/category/1")
					.contentType(MediaType.APPLICATION_JSON).content(updateCategoryAsJSON);
			
			ResultMatcher matchStatusPut = status().isNotFound();
			
			ResultMatcher matchBodyPut = content().json(updateCategoryAsJSON);
			
			this.mock.perform(mockPutRequest).andExpect(matchStatusPut).
			andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(result -> assertEquals("CategoryId 1 not found", result.getResolvedException().getMessage()));
		}
		
		
}
