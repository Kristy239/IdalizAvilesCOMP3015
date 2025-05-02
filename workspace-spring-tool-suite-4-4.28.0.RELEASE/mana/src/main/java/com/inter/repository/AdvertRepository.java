package com.inter.repository;

import com.inter.model.Advert;
import com.inter.model.AdvertCategory;
import com.inter.model.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import jakarta.transaction.Transactional;

@Transactional
public interface AdvertRepository extends CrudRepository<Advert, Long> {
	
	public List<Advert> findByOwner(User owner);
	public List<Advert> findByCategory(AdvertCategory category);
	public List<Advert> findByTitleLike(String titlePattern);
	public List<Advert> findByCategoryAndTitleLike(AdvertCategory category, String title);
}