package com.inter.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.inter.model.Advert;
import com.inter.model.AdvertCategory;
import com.inter.model.User;
import com.inter.repository.AdvertCategoryRepository;
import com.inter.repository.AdvertRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("advert")
public class AdvertController {

	@Autowired
	AdvertRepository advertRepository;

	@Autowired
	AdvertCategoryRepository advertCategoryRepository;

	@GetMapping("/{id}")
	public String getAdvertDetails(@PathVariable Long id, Map<String, Object> model) {
		Advert advert = this.advertRepository.findById(id).orElse(null);

		if (advert != null) {
			String base64Image = Base64.getEncoder().encodeToString(advert.getImage());
			model.put("image", base64Image);
		}

		model.put("advert", advert);
		model.put("advertCategories", advertCategoryRepository.findAll());

		return "advertDetails";
	}

	@GetMapping("/create")
	public String create(Map<String, Object> model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			model.put("advertCategories", advertCategoryRepository.findAll());
			return "createAdvert";

		} else {
			return "redirect:/login";

		}
	}
	
	@GetMapping("/modify")
	public String create(Map<String, Object> model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			model.put("advertCategories", advertCategoryRepository.findAll());
			return "createAdvert";

		} else {
			return "redirect:/login";

		}
	}

	@PostMapping
	public String newAdvert(MultipartHttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			Iterator<String> itr = request.getFileNames();

			try {
				// Load the first image
				byte[] image = request.getFile(itr.next()).getBytes();
				
				Long categoryId = Long.parseLong(request.getParameter("advertCategory"));
				AdvertCategory category = advertCategoryRepository.findById(categoryId).orElse(null);
				User owner = (User) auth.getPrincipal();
				String title = request.getParameter("title");
				String description = request.getParameter("description");
				float cost = Float.parseFloat(request.getParameter("cost"));

				Advert newAdvert = new Advert();
				newAdvert.setCategory(category);
				newAdvert.setOwner(owner);
				newAdvert.setTitle(title);
				newAdvert.setDescription(description);
				newAdvert.setImage(image);
				newAdvert.setCost(cost);

				advertRepository.save(newAdvert);

				return ("redirect:/advert/" + String.valueOf(newAdvert.getID()));
				
			} catch (IOException e) {
				// Show this page again with an error if there was an issue
				return "redirect:/advert/create?error=true"
			}
			
		} else {
			// Go home if the user is not authenticated
			return "redirect:/";
		}

		return "redirect:/account";
	}

	
	@PutMapping
	public String updateAdvert(Map<String, Object> model) {
		return "account";
	}

	@DeleteMapping
	public String deleteAdvert(Map<String, Object> model) {
		return "account";
	}
}
