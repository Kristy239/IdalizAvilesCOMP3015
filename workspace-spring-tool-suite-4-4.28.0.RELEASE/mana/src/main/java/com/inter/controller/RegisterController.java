package com.inter.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inter.model.User;
import com.inter.service.UserService;

@Controller
@RequestMapping("register")
public class RegisterController {

	@Autowired
	UserService userService;

	@GetMapping
	public String register(Map<String, Object> model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			return "redirect:/user";

		} else {
			model.put("newUser", new User());
			return "register";
		}
	}

	@PostMapping
	public String createUser(@ModelAttribute User newUser, Map<String, Object> model) {
		newUser = this.userService.createUser(newUser);

		if (newUser == null) {
			return "redirect:/register?error=true";

		} else {
			return "redirect:/login";
		}
	}
}
