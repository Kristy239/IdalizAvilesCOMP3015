package com.inter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.inter.model.AdvertCategory;
import com.inter.repository.AdvertCategoryRepository;

import jakarta.annotation.Resource;

@Service("advertCategoryService")
public class AdvertCategoryServiceImpl implements AdvertCategoryService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Resource
	private AdvertCategoryRepository advertCategoryRepository;

	AdvertCategoryServiceImpl() {
	}

	@Override
	public List<AdvertCategory> getAdvertCategories() {
		return (List<AdvertCategory>) this.advertCategoryRepository.findAll();
	}

	@Override
	public AdvertCategory getAdvertCategory(Long ID) {
		Optional<AdvertCategory> result = this.advertCategoryRepository.findById(ID);
		if (result.isPresent()) {
			return result.get();
		} else {
			return new AdvertCategory();
		} 
	}

	@Override
	public AdvertCategory createAdvertCategory(AdvertCategory c) {
		this.advertCategoryRepository.save(c);
		return c;
	}

	@Override
	public AdvertCategory updateAdvertCategory(AdvertCategory c) {
		this.advertCategoryRepository.save(c);
		return c;
	}

	@Override
	public boolean deleteAdvertCategory(AdvertCategory c) {
		this.advertCategoryRepository.delete(c);
		return true;
	}

}
