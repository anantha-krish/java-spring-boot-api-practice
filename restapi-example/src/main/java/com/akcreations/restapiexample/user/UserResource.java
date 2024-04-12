package com.akcreations.restapiexample.user;

import java.net.URI;
import java.util.List;

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
public class UserResource {
	@Autowired
	private UserDaoService userService;

	@GetMapping(path = "/users")
	public List<User> getAllUsers() {
		return userService.findAllUsers();
	}

	@GetMapping(path = "/users/{id}")
	public EntityModel<User> getUser(@PathVariable("id") int id) {
		User user = userService.findUser(id).orElse(null);
		if (user == null) {
			throw new UserNotFoundException("User not found " + id);
		}
		EntityModel<User> entity = EntityModel.of(user);
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(
						this.getClass()).getAllUsers());
		entity.add(link.withRel("all-users"));
		return entity;
	}

	@DeleteMapping(path = "/users/{id}")
	public void removeUser(@PathVariable("id") int id) {
		userService.deleteById(id);
	}

	@PostMapping(path = "/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User newUser) {
		User savedUser = userService.addUser(newUser);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		// appends uri to header
		return ResponseEntity.created(location).build();
	}

}
