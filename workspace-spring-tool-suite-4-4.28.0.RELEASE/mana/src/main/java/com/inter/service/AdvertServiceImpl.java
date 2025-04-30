package com.inter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.inter.model.Advert;
import com.inter.repository.AdvertRepository;

import jakarta.annotation.Resource;

@Service("advertService")
public class AdvertServiceImpl implements AdvertService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Resource
	private AdvertRepository advertRepository;

	AdvertServiceImpl() {
	}

	@Override
	public List<Advert> getAdverts() {
		return (List<Advert>) this.advertRepository.findAll();
	}

	@Override
	public Advert getAdvert(Long ID) {
		Optional<Advert> result = this.advertRepository.findById(ID);
		if (result.isPresent()) {
			return result.get();
		} else {
			return new Advert();
		} 
	}

	@Override
	public Advert createAdvert(Advert c) {
		this.advertRepository.save(c);
		return c;
	}

	@Override
	public Advert updateAdvert(Advert c) {
		this.advertRepository.save(c);
		return c;
	}

	@Override
	public boolean deleteAdvert(Advert c) {
		this.advertRepository.delete(c);
		return true;
	}

}
