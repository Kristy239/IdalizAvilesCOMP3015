package com.inter.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inter.helpers.FormFriendlyAdvert;
import com.inter.model.User;
import com.inter.repository.AdvertCategoryRepository;
import com.inter.repository.AdvertRepository;

@Controller
@RequestMapping("account")
public class AccountController {

	@Autowired
	AdvertRepository advertRepository;

	@Autowired
	AdvertCategoryRepository advertCategoryRepository;

	@GetMapping
	public String account(Map<String, Object> model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<FormFriendlyAdvert> adverts = advertRepository.findByOwner(user).stream().map(advert -> new FormFriendlyAdvert(advert))
				.collect(Collectors.toList());
		model.put("user", user);
		model.put("adverts", adverts);
		model.put("advertCategories", advertCategoryRepository.findAll());
		return "account";
	}
}