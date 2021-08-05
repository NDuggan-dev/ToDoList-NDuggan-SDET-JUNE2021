package com.qa.spring.todo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import  static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "Task")
@Table(name = "task")
public class Task {
	
	@Id
	@SequenceGenerator(name="task_sequence", sequenceName="task_sequence", allocationSize=1)
	@GeneratedValue(strategy = SEQUENCE, generator = "task_sequence")
	@Column(name ="id", updatable=false)
	private Long id;
	
	@Column(name="title", nullable=false, columnDefinition="TEXT")
	private String title;
	
	@Column(name="description", nullable=false, columnDefinition="TEXT")
	private String description;
	
	@Column(name="dueDate", nullable=false, columnDefinition="DATE")
	private LocalDate dueDate;
	
	@Column(name="priority", nullable=false, columnDefinition="TEXT")
	@Enumerated(EnumType.STRING)
	private PriorityEnum priority;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "categoryId", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Category category;
	
	public Task() {
		
	}
	
	public Task(Long id, String title, String description, LocalDate dueDate, PriorityEnum priority,
			Category category) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public PriorityEnum getPriority() {
		return priority;
	}

	public void setPriority(PriorityEnum priority) {
		this.priority = priority;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", dueDate=" + dueDate
				+ ", priority=" + priority + ", category=" + category + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, description, dueDate, id, priority, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(category, other.category) && Objects.equals(description, other.description)
				&& Objects.equals(dueDate, other.dueDate) && Objects.equals(id, other.id) && priority == other.priority
				&& Objects.equals(title, other.title);
	}


	

}
