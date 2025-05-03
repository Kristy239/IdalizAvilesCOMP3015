package com.inter.helpers;

import com.inter.model.AdvertCategory;

public class SearchForm {
	private String query;
	private AdvertCategory category;
	
	public SearchForm(){
		super();
	}
	
	public SearchForm(String query, AdvertCategory category) {
		this.setQuery(query);
		this.setCategory(category);
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public AdvertCategory getCategory() {
		return category;
	}

	public void setCategory(AdvertCategory category) {
		this.category = category;
	}
	
}
