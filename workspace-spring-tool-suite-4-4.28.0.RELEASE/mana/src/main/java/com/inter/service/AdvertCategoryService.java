package com.inter.service;

import java.util.List;

import com.inter.model.AdvertCategory;

public interface AdvertCategoryService {
	public List<AdvertCategory> getAdvertCategories();
	public AdvertCategory getAdvertCategory(Long ID);
	public AdvertCategory createAdvertCategory(AdvertCategory c);
	public AdvertCategory updateAdvertCategory(AdvertCategory c);
	public boolean deleteAdvertCategory(AdvertCategory c);
}
