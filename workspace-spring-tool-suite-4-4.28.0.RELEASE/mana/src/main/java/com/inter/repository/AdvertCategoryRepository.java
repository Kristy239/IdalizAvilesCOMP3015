package com.inter.repository;

import com.inter.model.AdvertCategory;

import org.springframework.data.repository.CrudRepository;
import jakarta.transaction.Transactional;

@Transactional
public interface AdvertCategoryRepository extends CrudRepository<AdvertCategory, Long> {
	
}