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
import org.springframework.web.bind.annotation.RestController;

import com.qa.spring.todo.domain.Task;
import com.qa.spring.todo.service.TaskService;
@CrossOrigin
@RestController
@RequestMapping("/task")
public class TaskController {
	
	private TaskService service; 
	
	@Autowired
	public TaskController(TaskService service) {
		this.service = service; 
	}
	
	@GetMapping("/getall")
	public ResponseEntity<List<Task>> getAll(){
		return new ResponseEntity<List<Task>>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{categoryid}/getallbycategory")
	public ResponseEntity<List<Task>> getAllByCategory(@PathVariable (value="categoryid") Long categoryId){
		return new ResponseEntity<List<Task>>(service.getAllByCategory(categoryId), HttpStatus.OK);
	}
	
	@PostMapping("/{categoryid}/create")
	public ResponseEntity<Task> create(@PathVariable (value="categoryid") Long categoryId, @Valid @RequestBody Task task){
		return new ResponseEntity<>(this.service.create(categoryId, task), HttpStatus.CREATED);
	}

	@PutMapping("/{categoryid}/{taskid}/update")
	public ResponseEntity<Task> update(@PathVariable (value="categoryid") Long categoryId, 
			@PathVariable (value="taskid") Long taskId, @Valid @RequestBody Task task){
		return new ResponseEntity<>(this.service.update(categoryId, taskId, task), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{taskid}")
	public ResponseEntity<?> deleteTask(@PathVariable (value="taskid") Long id){
		if(this.service.deleteTask(id)) {
			return new ResponseEntity<>(id, HttpStatus.OK);
		}else {
			return ResponseEntity.internalServerError().build();
		}
	}
}
