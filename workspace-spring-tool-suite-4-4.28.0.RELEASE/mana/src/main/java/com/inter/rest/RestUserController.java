package com.inter.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inter.model.User;
import com.inter.service.UserService;

@RestController
@RequestMapping("rest/user")
public class RestUserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Collection<User> getUsers(){
		return this.userService.getUsers();
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public User createUser(@RequestBody User user) {
		return this.userService.createUser(user);
	}
	
	@PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public User updateUser(@RequestBody User user) {
		return this.userService.updateUser(user);
	}
	
	@DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public boolean deleteUser(@RequestBody User user) {
		return this.userService.deleteUser(user);
	}


}
