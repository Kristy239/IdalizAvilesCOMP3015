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

import com.inter.model.Advert;
import com.inter.service.AdvertService;

@RestController
@RequestMapping("rest/advert")
public class RestAdvertController {
	
	@Autowired
	AdvertService advertService;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Collection<Advert> getAdverts(){
		return this.advertService.getAdverts();
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Advert createAdvert(@RequestBody Advert advert) {
		return this.advertService.createAdvert(advert);
	}
	
	@PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Advert updateAdvert(@RequestBody Advert advert) {
		return this.advertService.updateAdvert(advert);
	}
	
	@DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public boolean deleteAdvert(@RequestBody Advert advert) {
		return this.advertService.deleteAdvert(advert);
	}


}
