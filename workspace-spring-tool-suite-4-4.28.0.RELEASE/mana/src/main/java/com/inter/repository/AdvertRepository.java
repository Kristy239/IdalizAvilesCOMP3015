package com.inter.repository;

import com.inter.model.Advert;

import org.springframework.data.repository.CrudRepository;
import jakarta.transaction.Transactional;

@Transactional
public interface AdvertRepository extends CrudRepository<Advert, Long> {
	
}