package com.inter.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inter.model.AdvertCategory;
import com.inter.service.AdvertCategoryService;

@RestController
@RequestMapping("rest/advertcategory")
public class RestAdvertCategoryController {
	
	@Autowired
	AdvertCategoryService advertCategoryService;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Collection<AdvertCategory> getAdvertCategories(){
		return this.advertCategoryService.getAdvertCategories();
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public AdvertCategory createAdvertCategory(@RequestBody AdvertCategory advertCategory) {
		return this.advertCategoryService.createAdvertCategory(advertCategory);
	}
	
	@PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public AdvertCategory updateAdvertCategory(@RequestBody AdvertCategory advertCategory) {
		return this.advertCategoryService.updateAdvertCategory(advertCategory);
	}
	
	@DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public boolean deleteAdvertCategory(@RequestBody AdvertCategory advertCategory) {
		return this.advertCategoryService.deleteAdvertCategory(advertCategory);
	}


}
