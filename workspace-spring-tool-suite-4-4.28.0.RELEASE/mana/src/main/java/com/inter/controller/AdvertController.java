package com.inter.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inter.helpers.FormFriendlyAdvert;
import com.inter.model.Advert;
import com.inter.model.User;
import com.inter.repository.AdvertCategoryRepository;
import com.inter.repository.AdvertRepository;

@Controller
@RequestMapping("advert")
public class AdvertController {

	@Autowired
	AdvertRepository advertRepository;

	@Autowired
	AdvertCategoryRepository advertCategoryRepository;

	@GetMapping("/{id}")
	public String getAdvertDetails(@PathVariable Long id, Map<String, Object> model) {
		FormFriendlyAdvert advert = new FormFriendlyAdvert(this.advertRepository.findById(id).orElse(new Advert()));
		model.put("advertCategories", advertCategoryRepository.findAll());

		if (advert.getID() == null) {
			model.put("advert", null);
		} else {
			model.put("advert", advert);
		}

		return "advertDetails";
	}

	@GetMapping("/create")
	public String create(Map<String, Object> model) {
		// Check if the user is authenticated
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			model.put("formData", new FormFriendlyAdvert());
			model.put("method", "post");
			model.put("advertCategories", advertCategoryRepository.findAll());
			return "createAdvert";

		} else {
			// Redirect the user to log in if they are not authenticated
			System.err.println("AdvertController: Trying to create advert with unauthenticated user");
			return "redirect:/login";
		}
	}

	@GetMapping("/modify/{id}")
	public String modify(@PathVariable Long id, Map<String, Object> model) {
		// Check if the user is authenticated
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			FormFriendlyAdvert advert = new FormFriendlyAdvert(advertRepository.findById(id).orElse(new Advert()));

			if (advert.getOwner().getID() != ((User) auth.getPrincipal()).getID()) {
				System.err.print("Advert with id ");
				System.err.print(id);
				System.err.print(" is owned by ");
				System.err.print(advert.getOwner().getID());
				System.err.print(" but was attempted to be modified by the user with id ");
				System.err.println(((User) auth.getPrincipal()).getID());
				return "redirect:/account?modificationDenied=true";
			}

			model.put("formData", advert);
			model.put("method", "put");
			model.put("advertCategories", advertCategoryRepository.findAll());
			return "createAdvert";

		} else {
			// Redirect the user to log in if they are not authenticated
			System.err.println("AdvertController: Trying to modify advert with unauthenticated user");
			return "redirect:/login";
		}
	}

	@PostMapping(consumes = "multipart/form-data")
	public String newAdvert(@ModelAttribute("formData") FormFriendlyAdvert formData) {
		// Check if the user is authenticated
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			Advert newAdvert = formData.toAdvert();
			newAdvert.setOwner((User) auth.getPrincipal());
			advertRepository.save(newAdvert);

			if (newAdvert.getID() != null) {
				return ("redirect:/advert/" + String.valueOf(newAdvert.getID()));
			} else {
				return "redirect:/account?creationError=true";
			}

		} else {
			// Redirect the user to log in if they are not authenticated
			System.err.println("AdvertController: Trying to create advert with unauthenticated user");
			return "redirect:/login";
		}
	}

	@PutMapping(value = "/{id}", consumes = "multipart/form-data")
	public String updateAdvert(@PathVariable Long id, @ModelAttribute("formData") FormFriendlyAdvert formData) {
		// Check if the user is authenticated
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			formData.setID(id);
			formData.setOwner((User) auth.getPrincipal());
			Advert originalAdvert = advertRepository.findById(formData.getID()).orElse(null);
			if (originalAdvert != null && formData.getOwner().getID() != originalAdvert.getOwner().getID()) {
				System.err.print("Advert with id ");
				System.err.print(id);
				System.err.print(" is owned by ");
				System.err.print(originalAdvert.getOwner().getID());
				System.err.print(" but was attempted to be modified by the user with id ");
				System.err.println(((User) auth.getPrincipal()).getID());

				return "redirect:/account?error=true";
			}

			Advert modifiedAdvert = new Advert(formData.getID(),
					(formData.getTitle().isBlank() ? originalAdvert.getTitle() : formData.getTitle()),
					(formData.getDescription().isBlank() ? originalAdvert.getDescription() : formData.getDescription()),
					(formData.getCost() == 0 && originalAdvert.getCost() > 0 ? originalAdvert.getCost()
							: formData.getCost()),
					(formData.getImageBytes().length == 0 ? originalAdvert.getImage() : formData.getImageBytes()),
					(formData.getImageBytes().length == 0 ? originalAdvert.getImageContentType()
							: formData.getImageContentType()),
					formData.getOwner(), formData.getCategory());
			Advert storedAdvert = advertRepository.save(modifiedAdvert);

			if (storedAdvert != null && storedAdvert.getID() != null) {
				return ("redirect:/account?modifySuccess=true");
			} else {
				return "redirect:/account?modifySuccess=false";
			}
		} else {
			return "redirect:/login";

		}
	}

	@DeleteMapping("/{id}")
	public String deleteAdvert(@PathVariable Long id, Map<String, Object> model) {

		if (advertRepository.existsById(id)) {
			advertRepository.deleteById(id);
			return "redirect:/account?deleteSuccess=true";
		} else {
			return "redirect:/account?deleteSuccess=false";
		}
	}
}