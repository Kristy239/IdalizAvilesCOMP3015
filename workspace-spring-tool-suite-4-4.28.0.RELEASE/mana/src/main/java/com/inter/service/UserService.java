package com.inter.service;

import java.util.List;

import com.inter.model.User;

public interface UserService {
	public List<User> getUsers();
	public User getUser(Long ID);
	public User createUser(User u);
	public User updateUser(User u);
	public boolean deleteUser(User u);
}
