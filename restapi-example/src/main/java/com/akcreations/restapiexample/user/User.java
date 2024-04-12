package com.akcreations.restapiexample.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity(name="user_details")
public class User {
    @Id
    @GeneratedValue
	private int id;

	@Size(min = 2, message = "Name should have 2 values")
	public String name;

	@Past(message = "Kindly select a past date")
	public LocalDate date;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	List<Post> posts;

	// Empty constructor needed for Request body mapping
	protected User() {
		super();
		this.id = 0;
		this.name = "";
		this.date = LocalDate.now();
	}

	public User(int id, String name, LocalDate date) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", date=" + date + "]";
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

}
