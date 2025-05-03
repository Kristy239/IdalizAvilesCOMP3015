package com.inter.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inter.helpers.FormFriendlyAdvert;
import com.inter.helpers.SearchForm;
import com.inter.model.Advert;
import com.inter.model.AdvertCategory;
import com.inter.model.User;
import com.inter.repository.AdvertCategoryRepository;
import com.inter.repository.AdvertRepository;

@Controller
@RequestMapping("search")
public class SearchController {

	@Autowired
	AdvertRepository advertRepository;

	@Autowired
	AdvertCategoryRepository advertCategoryRepository;
//
//	@PostMapping("/")
//	public String all(@RequestParam("query") String query, Map<String, Object> model) {
//		List<FormFriendlyAdvert> adverts = advertRepository.findByTitleContainingOrContentContaining(query).stream()
//				.map(advert -> new FormFriendlyAdvert(advert)).collect(Collectors.toList());
//		model.put("adverts", adverts);
//		model.put("advertCategories", advertCategoryRepository.findAll());
//		return "results";
//	}

	@GetMapping
	public String searchBar(@ModelAttribute("SearchForm") SearchForm searchForm, Map<String, Object> model) {
		List<Advert> retrievedAdverts;
		if (!searchForm.getQuery().isBlank() && searchForm.getCategory() == null) {
			retrievedAdverts = advertRepository.findByTitleContainingIgnoreCase(searchForm.getQuery());

		} else if (searchForm.getQuery().isBlank() && searchForm.getCategory() != null) {
			retrievedAdverts = advertRepository.findByCategory(searchForm.getCategory());
		} else if (!searchForm.getQuery().isBlank() && searchForm.getCategory() != null) {
			retrievedAdverts = advertRepository.findByCategoryAndTitleContainingIgnoreCase(searchForm.getCategory(),
					searchForm.getQuery());
		} else {
			retrievedAdverts = (List<Advert>) advertRepository.findAll();
		}

		List<FormFriendlyAdvert> adverts = retrievedAdverts.stream().map(advert -> new FormFriendlyAdvert(advert))
				.collect(Collectors.toList());
		model.put("adverts", adverts);
		model.put("searchForm", new SearchForm());
		model.put("advertCategories", advertCategoryRepository.findAll());
		return "results";
	}

	@GetMapping("/category")
	public String specificCategory(@RequestParam(value = "category", required = true) AdvertCategory category,
			Map<String, Object> model) {
		List<Advert> retrievedAdverts;

		if (category == null) {
			retrievedAdverts = (List<Advert>) advertRepository.findAll();
		} else {
			retrievedAdverts = advertRepository.findByCategory(category);
		}

		List<FormFriendlyAdvert> adverts = retrievedAdverts.stream().map(advert -> new FormFriendlyAdvert(advert))
				.collect(Collectors.toList());
		model.put("adverts", adverts);
		model.put("searchForm", new SearchForm());
		model.put("advertCategories", advertCategoryRepository.findAll());
		return "results";
	}

}
