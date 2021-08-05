package com.qa.springzoo.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.spring.todo.domain.Zoo;
import com.qa.spring.todo.repo.TaskRepo;
import com.qa.spring.todo.service.TaskService;

@SpringBootTest
public class ZooServiceUnitTest {

	@MockBean
	private TaskRepo repo; 
	
	@Autowired
	private TaskService service; 
	
	
	@Test
	void testCreateUnit() {
		// GIVEN 
			
		Zoo midland = new Zoo("Midland Safari Park",2000);
		Zoo midlandWithId = new Zoo(1L,"Midland Safari Park",2000);
		
		// WHEN 
		Mockito.when(this.repo.saveAndFlush(midland)).thenReturn(midlandWithId);
		
		
		// THEN
		assertThat(this.service.create(midland)).isEqualTo(midlandWithId);
		
		Mockito.verify(this.repo, Mockito.times(1)).saveAndFlush(midland);
		
	}
	
	
	
}
