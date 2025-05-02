package com.inter.repository;

import com.inter.model.Advert;
import com.inter.model.User;

import org.springframework.data.repository.CrudRepository;
import jakarta.transaction.Transactional;

@Transactional
public interface AdvertRepository extends CrudRepository<Advert, Long> {
	
	public <List>Advert findByOwner(User owner);
}