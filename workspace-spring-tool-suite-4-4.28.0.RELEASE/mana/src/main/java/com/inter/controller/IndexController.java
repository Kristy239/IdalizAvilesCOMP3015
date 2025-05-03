package com.inter.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.inter.helpers.SearchForm;
import com.inter.repository.AdvertCategoryRepository;

@Controller
public class IndexController {

	@Autowired
	AdvertCategoryRepository advertCategoryRepository;

	@GetMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("searchForm", new SearchForm());
		model.put("advertCategories", advertCategoryRepository.findAll());

		return "index";
	}

}
