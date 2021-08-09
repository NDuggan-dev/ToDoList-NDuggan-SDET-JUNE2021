package com.qa.spring.todo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CategoryTest {
		@Test 
		public void testConstructorOne() {
			Category category = new Category(1L, "Shopping", "#FFF");
			assertTrue (category instanceof Category);
		}
		
		@Test 
		public void testConstructorTwo() {
			Category category = new Category();
			assertTrue(category instanceof Category);
		}
		
		@Test 
		public void testGetters() {
			Category category = new Category(1L, "Shopping", "#FFF");
			Long expectedId = 1L;
			String expectedName = "Shopping";
			String expectedColour = "#FFF";
			
			assertEquals(expectedId, category.getId());
			assertEquals(expectedName, category.getName());
			assertEquals(expectedColour, category.getColour());
		}
		
		@Test
		public void testSetters() {
			Category category = new Category(1L, "Shopping", "#FFF");
			Long expectedId = 2L;
			category.setId(expectedId);
			String expectedName = "Shopping List";
			category.setName(expectedName);
			String expectedColour = "#FFFF";
			category.setColour(expectedColour);
		
			
			assertEquals(expectedId, category.getId());
			assertEquals(expectedName, category.getName());
			assertEquals(expectedColour, category.getColour());
		}
		
		@Test 
		public void testToString() {
			Category category = new Category(1L, "Shopping", "#FFF");
			String expected = "Category [id=" + category.getId() + ", name=" + category.getName() + ", colour=" + category.getColour() + "]";
			assertEquals(expected, category.toString());
		}
	
		
		@Test
		public void testEqualsAndHashCode() {
			Category category = new Category(1L, "Shopping", "#FFF");
			Category category2 = new Category(1L, "Shopping", "#FFF");
			assertTrue(category.equals(category2) && category2.equals(category));
			assertTrue(category.hashCode() == category2.hashCode());
		}
		
		@Test 
		public void testNullHashCodeEquals() {
			Category category = new Category(null, null, null);
			Category category2 = new Category(1L, "Shopping", "#FFF");
			assertFalse(category.equals(category2) && category2.equals(category));
			assertFalse(category.hashCode() == category2.hashCode());
			
		}
}

