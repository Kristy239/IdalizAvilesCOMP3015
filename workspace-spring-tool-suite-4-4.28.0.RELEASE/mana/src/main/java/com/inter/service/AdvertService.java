package com.inter.service;

import java.util.List;

import com.inter.model.Advert;

public interface AdvertService {
	public List<Advert> getAdverts();
	public Advert getAdvert(Long ID);
	public Advert createAdvert(Advert c);
	public Advert updateAdvert(Advert c);
	public boolean deleteAdvert(Advert c);
}
