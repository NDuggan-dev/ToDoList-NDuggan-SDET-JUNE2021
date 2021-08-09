package com.qa.spring.todo.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qa.spring.todo.domain.Category;
import com.qa.spring.todo.service.CategoryService;
@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {
	
	// Dependency
	private CategoryService service; 
	
	@Autowired
	public CategoryController(CategoryService service) {
		this.service = service; 
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<Category>> getAll(){
		return new ResponseEntity<List<Category>>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{categoryid}")
	public ResponseEntity<Category> getById(@PathVariable (value="categoryid") Long categoryid){
		return new ResponseEntity<Category>(service.getById(categoryid), HttpStatus.OK);
	}
	

	
	@PostMapping()
	public ResponseEntity<Category> createCategory(@RequestBody Category category){
		return new ResponseEntity<>(this.service.createCategory(category), HttpStatus.CREATED);
	}
	

	@PutMapping("/{categoryid}")
	public ResponseEntity<Category> updateCategory(@PathVariable (value="categoryid") Long categoryId, @Valid @RequestBody Category category){
		if(category.getName() == null || category.getColour() == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	   return new ResponseEntity<>(this.service.updateCategory(category, categoryId), HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryid}")
	public ResponseEntity<?> deleteCategory(@PathVariable (value="categoryid") Long categoryId){
		if(this.service.deleteCategory(categoryId)) {
			return new ResponseEntity<>(categoryId, HttpStatus.OK);
		}else {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	
	
	
}
