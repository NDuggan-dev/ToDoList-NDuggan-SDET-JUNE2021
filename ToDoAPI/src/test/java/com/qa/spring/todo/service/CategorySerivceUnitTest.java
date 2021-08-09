package com.qa.spring.todo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.spring.todo.domain.Category;
import com.qa.spring.todo.domain.builder.CategoryBuilder;
import com.qa.spring.todo.repo.CategoryRepo;


@SpringBootTest
public class CategorySerivceUnitTest {
	
	@MockBean
	private CategoryRepo categoryRepo;
	
	@Autowired
	private CategoryService categoryService;
	
	@Test
	void testCreateCategory() {
		Category category = new CategoryBuilder().name("Shopping").colour("#ff3e05").build();
		Category expectedCategory = new CategoryBuilder().id(1L).name("Shopping").colour("#ff3e05").build();
		
		Mockito.when(this.categoryRepo.saveAndFlush(category)).thenReturn(expectedCategory);
		
		assertThat(this.categoryService.createCategory(category)).isEqualTo(expectedCategory);
		
		Mockito.verify(this.categoryRepo, Mockito.times(1)).saveAndFlush(category);	
	}
	
	@Test
	void testGetAllCategory() {
		Category category1 = new CategoryBuilder().id(1L).name("Shopping").colour("#ff3e05").build();
		Category category2 = new CategoryBuilder().id(2L).name("Shopping").colour("#ff3e05").build();
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(category1);
		categoryList.add(category2);
		
		Mockito.when(this.categoryRepo.findAll()).thenReturn(categoryList);
		
		assertThat(this.categoryService.getAll()).isEqualTo(categoryList);
		
		Mockito.verify(this.categoryRepo, Mockito.times(1)).findAll();	
	}
	
	@Test
	void testUpdateCategory() {
		Optional<Category> categoryToBeUpdated = Optional.of(new CategoryBuilder().id(1L).name("Shopping").colour("#ff3e05").build());
		Category categoryUpdated = new CategoryBuilder().id(1L).name("Shopping List").colour("#ff3e05").build();
		Long id = 1L;
		
		Mockito.when(this.categoryRepo.findById(id)).thenReturn(categoryToBeUpdated);
		Mockito.when(this.categoryRepo.save(categoryUpdated)).thenReturn(categoryUpdated);
		
		assertThat(this.categoryService.updateCategory(categoryUpdated, id)).isEqualTo(categoryUpdated);
		
		Mockito.verify(this.categoryRepo, Mockito.times(1)).findById(id);
		Mockito.verify(this.categoryRepo, Mockito.times(1)).save(categoryUpdated);
	}
	
	@Test 
	void testDeleteCategory() {
		Optional<Category> category= Optional.of(new CategoryBuilder().id(1L).name("Shopping").colour("#ff3e05").build());
		Long id = 1L;
		
		Mockito.when(this.categoryRepo.findById(id)).thenReturn(category);
		
		assertThat(this.categoryService.deleteCategory(id)).isEqualTo(true);
		
		Mockito.verify(this.categoryRepo, Mockito.times(1)).findById(id);	
	}
	

}
