package com.qa.spring.todo.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Category")
@Table(name = "category")
public class Category {
		
		@Id
		@SequenceGenerator(name="category_sequence", sequenceName="category_sequence", allocationSize=1)
		@GeneratedValue(strategy = SEQUENCE, generator = "category_sequence")
		@Column(name ="categoryId", updatable=false)
		private Long id;
		
		@Column(name="name", nullable=false, columnDefinition="TEXT")
		private String name;
		
		@Column(name="colour", nullable=false, columnDefinition="TEXT")
		private String colour;
		
		public Category(Long id, String name, String colour) {
			super();
			this.id = id;
			this.name = name;
			this.colour = colour;
		}
		
		public Category() {
			
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getColour() {
			return colour;
		}

		public void setColour(String colour) {
			this.colour = colour;
		}

		@Override
		public int hashCode() {
			return Objects.hash(colour, id, name);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Category other = (Category) obj;
			return Objects.equals(colour, other.colour) && Objects.equals(id, other.id)
					&& Objects.equals(name, other.name);
		}

		@Override
		public String toString() {
			return "Category [id=" + id + ", name=" + name + ", colour=" + colour + "]";
		}

	
}
