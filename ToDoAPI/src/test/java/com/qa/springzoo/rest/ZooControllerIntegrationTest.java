package com.qa.springzoo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.spring.todo.domain.Zoo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ZooControllerIntegrationTest {

	// Mock Controller and relevant mappers
	
	@Autowired
	private MockMvc mock; 
	
	@Autowired
	private ObjectMapper mapper; // CONVERT REQUESTS TO JSON
	
	@Test
	void testCreate() throws Exception {
		
		// 1. Create our ZOO Object
		Zoo zoo = new Zoo("West Midlands Safari Park",2000);
		
		// 2. Convert it to a JSON String
		String zooAsJSON = this.mapper.writeValueAsString(zoo);
		
		// 3. Build our mock request
		
		RequestBuilder mockRequest = 
							post("/createZoo")
							.contentType(MediaType.APPLICATION_JSON)
							.content(zooAsJSON);
		
		// 4. Create our "saved" zoo 
		Zoo savedZoo = new Zoo(1L,"West Midlands Safari Park",2000);
		
		// 5. Convert SAVED zoo into JSON String
		String savedZooAsJSON = this.mapper.writeValueAsString(savedZoo);
		
		// 6. Check if the Status is 201 - CREATED
		ResultMatcher matchStatus = status().isCreated(); 
		
		// 7. Check if the body is the correct "saved" zoo 
		ResultMatcher matchBody = content().json(savedZooAsJSON);
		
		// 8. Build the request
		this.mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
	}
	
	
}
