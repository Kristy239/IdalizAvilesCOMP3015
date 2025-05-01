package com.inter.repository;

import com.inter.model.User;

import org.springframework.data.repository.CrudRepository;
import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);
}