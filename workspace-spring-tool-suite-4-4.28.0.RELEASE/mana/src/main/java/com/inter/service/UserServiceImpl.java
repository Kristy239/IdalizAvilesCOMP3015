package com.inter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inter.model.User;
import com.inter.repository.UserRepository;

import jakarta.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Resource
	private UserRepository userRepository;

	UserServiceImpl() {
	}

	@Override
	public List<User> getUsers() {
		return (List<User>) this.userRepository.findAll();
	}

	@Override
	public User getUser(Long ID) {
		Optional<User> result = this.userRepository.findById(ID);
		if (result.isPresent()) {
			return result.get();
		} else {
			return new User();
		}
	}

	@Override
	@Transactional
	public User createUser(User u) {
		String rawPassword = u.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		u.setPassword(encodedPassword);
		this.userRepository.save(u);
		return u;
	}

	@Override
	public User updateUser(User u) {
		String rawPassword = u.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		u.setPassword(encodedPassword);
		this.userRepository.save(u);
		return u;
	}

	@Override
	public boolean deleteUser(User u) {
		this.userRepository.delete(u);
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = this.userRepository.findByUsername(username);

		return (UserDetails) u;
	}
}
