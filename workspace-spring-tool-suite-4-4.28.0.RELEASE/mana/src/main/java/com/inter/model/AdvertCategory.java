package com.inter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "advertcategory")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "advertcategory")
public class AdvertCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	@XmlAttribute
	private Long ID;
	
	@Column(name = "Name", nullable = false)
	@XmlAttribute
	private String name;
	
	public AdvertCategory() {
		super();
	}
	
	public AdvertCategory(Long ID, String name) {
		super();
		this.ID = ID;
		this.name = name;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
