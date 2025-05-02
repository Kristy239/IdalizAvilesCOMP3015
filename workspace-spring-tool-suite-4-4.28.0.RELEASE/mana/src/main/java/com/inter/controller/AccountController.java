package com.inter.controller;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inter.model.Advert;
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
		model.put("user", user);
		List<AdvertCard> adverts = advertRepository.findByOwner(user).stream().map(advert -> new AdvertCard(advert))
				.collect(Collectors.toList());
		model.put("adverts", adverts);
		model.put("advertCategories", advertCategoryRepository.findAll());

		return "account";
	}
}

class AdvertCard {
	private Long id;
	private String title;
	private String image;

	public AdvertCard() {
		super();
	}

	public AdvertCard(Advert advert) {
		super();
		this.setId(advert.getID());
		this.setTitle(advert.getTitle());
		this.setImage(advert.getImage());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setImage(byte[] image) {
		this.image = Base64.getEncoder().encodeToString(image);
	}

}