package com.akcreations.restapiexample.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserJPAResource {

	@Autowired
	private UserRepo repo;
	
	
	@Autowired
	private PostRepo postrepo;
	
	@GetMapping(path = "/jpa/users")
	public List<User> getAllUsers() {
		return repo.findAll();
	}

	@GetMapping(path = "/jpa/users/{id}")
	public EntityModel<User> getUser(@PathVariable("id") int id) {
		Optional<User> user = repo.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("User not found " + id);
		}
		EntityModel<User> entity = EntityModel.of(user.get());
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(
						this.getClass()).getAllUsers());
		entity.add(link.withRel("all-users"));
		return entity;
	}
	
	
	@GetMapping(path = "/jpa/users/{id}/posts")
	public List<Post> getPostsByUser(@PathVariable("id") int id) {
		Optional<User> user = repo.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("User not found " + id);
		}
	
		return user.get().getPosts();
	}

	
	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<Object> addPostByUser(@PathVariable("id") int id,@Valid @RequestBody Post post) {
		Optional<User> user = repo.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("User not found " + id);
		}
		
		post.setUser(user.get());
		Post savedPost=postrepo.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		// appends uri to header
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path = "/jpa/users/{id}")
	public void removeUser(@PathVariable("id") int id) {
	repo.deleteById(id);
	}

	@PostMapping(path = "/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User newUser) {
		User savedUser = repo.save(newUser);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		// appends uri to header
		return ResponseEntity.created(location).build();
	}

}
