package com.inter.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.inter.model.User;

public interface UserService extends UserDetailsService {
	public List<User> getUsers();

	public User getUser(Long ID);

	public User createUser(User u);

	public User updateUser(User u);

	public boolean deleteUser(User u);
}
