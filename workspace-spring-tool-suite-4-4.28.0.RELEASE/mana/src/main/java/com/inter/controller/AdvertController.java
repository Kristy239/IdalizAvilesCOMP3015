package com.inter.controller;

import java.io.IOException;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.inter.model.Advert;
import com.inter.model.AdvertCategory;
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
		// Retrieve the advert that has the given id
		Advert advert = this.advertRepository.findById(id).orElse(null);

		// If the advert has an image that can be put into the model, add it
		if (advert != null) {
			String base64Image = Base64.getEncoder().encodeToString(advert.getImage());
			model.put("image", base64Image);
		}

		// Required to populate the fields in the advert
		model.put("advert", advert);

		// Required for the navigation bar
		model.put("advertCategories", advertCategoryRepository.findAll());

		return "advertDetails";
	}

	@GetMapping("/create")
	public String create(Map<String, Object> model) {
		// Check if the user is authenticated
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {

			// If the user is authenticated, populate all the required fields in the model
			model.put("newFormData", new FormData());
			model.put("method", "post");

			// Required for the navigation bar
			model.put("advertCategories", advertCategoryRepository.findAll());

			return "createAdvert";

		} else {
			// Redirect the user to log in if they are not authenticated
			System.err.println("AdvertController: 75 Trying to create advert with unauthenticated user");
			return "redirect:/login";

		}
	}

	@GetMapping("/modify/{id}")
	public String modify(@PathVariable Long id, Map<String, Object> model) {
		// Check if the user is authenticated
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {

			// Begin with the baseline of what the database contains
			Advert advert = advertRepository.findById(id).orElse(null);

			// If the user requesting to modify the advert is not the owner, redirect back
			// to account
			if (advert.getOwner().getID() != ((User) auth.getPrincipal()).getID()) {
				System.err.print("Advert with id ");
				System.err.print(id);
				System.err.print(" is owned by ");
				System.err.print(advert.getOwner().getID());
				System.err.print(" but was attempted to be modified by the user with id ");
				System.err.println(((User) auth.getPrincipal()).getID());

				return "redirect:/account?error=true";
			}

			model.put("newFormData", new FormData(advert));
			model.put("method", "put");

			// Required for the navigation bar
			model.put("advertCategories", advertCategoryRepository.findAll());

			return "createAdvert";

		} else {
			// Redirect the user to log in if they are not authenticated
			System.err.println("AdvertController: 112 Trying to modify advert with unauthenticated user");
			return "redirect:/login";
		}
	}

	@PostMapping(consumes = "multipart/form-data")
	public String newAdvert(@ModelAttribute("newFormData") FormData newFormData) {
		// Check if the user is authenticated
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {

			// Convert the populated fields into a new advert
			Advert newAdvert = newFormData.convertToAdvert();

			// Fill some values that are not available in FormData
			newAdvert.setOwner((User) auth.getPrincipal());

			// Create the new advert
			advertRepository.save(newAdvert);

			// Redirect to the new advert if the ID is valid
			if (newAdvert.getID() != null) {
				return ("redirect:/advert/" + String.valueOf(newAdvert.getID()));
			} else {
				return "redirect:/account?error=true";
			}

		} else {
			// Go home if the user is not authenticated
			return "redirect:/";
		}
	}

	@PutMapping(value = "/{id}", consumes = "multipart/form-data")
	public String updateAdvert(@PathVariable Long id, @ModelAttribute("newFormData") FormData newFormData) {

		// Check if the user is authenticated
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
			// Get the baseline of what the database contains
			Advert originalAdvert = advertRepository.findById(newFormData.getId()).orElse(null);

			// If the user requesting to modify the advert is not the owner, redirect back
			// to account
			if (originalAdvert.getOwner().getID() != ((User) auth.getPrincipal()).getID()) {
				System.err.print("Advert with id ");
				System.err.print(id);
				System.err.print(" is owned by ");
				System.err.print(originalAdvert.getOwner().getID());
				System.err.print(" but was attempted to be modified by the user with id ");
				System.err.println(((User) auth.getPrincipal()).getID());

				return "redirect:/account?error=true";
			}

			// Take the form data and create a new modified advert
			Advert modifiedAdvert = newFormData.convertToAdvert();

			// Update the ID since the FromData must have created a new one
			modifiedAdvert.setID(id);
			// Update the owner since the FormData did not have this
			modifiedAdvert.setOwner(originalAdvert.getOwner());

			// Update the advert in the database
			Advert storedAdvert = advertRepository.save(modifiedAdvert);

			// Show success if the advert was updated successfully
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

class FormData {
	private Long id;
	private AdvertCategory category;
	private String title;
	private String description;
	private MultipartFile image;
	private float cost;

	public FormData() {
		super();
	}

	public FormData(Advert advert) {
		this.id = advert.getID();
		this.category = advert.getCategory();
		this.title = advert.getTitle();
		this.description = advert.getDescription();
		this.cost = advert.getCost();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AdvertCategory getCategory() {
		return category;
	}

	public void setCategory(AdvertCategory category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public Advert convertToAdvert() {
		Advert output = new Advert();
		output.setID(this.id);
		output.setCategory(this.category);
		output.setTitle(this.title);
		output.setDescription(this.description);
		output.setCost(this.cost);

		try {
			output.setImage(this.image.getBytes());
		} catch (IOException e) {
			output.setImage(new byte[0]);
		}

		return output;
	}
}
