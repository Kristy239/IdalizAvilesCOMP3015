package com.inter.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inter.model.User;
import com.inter.repository.AdvertCategoryRepository;

@Controller
@RequestMapping("account")
public class AccountController {

	@Autowired
	AdvertCategoryRepository advertCategoryRepository;

	@GetMapping
	public String account(Map<String, Object> model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.put("user", user);
		model.put("advertCategories", advertCategoryRepository.findAll());

		return "account";
	}
}
