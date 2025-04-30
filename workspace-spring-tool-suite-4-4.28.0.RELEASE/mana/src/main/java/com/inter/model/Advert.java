package com.inter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "advert")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "advert")
public class Advert {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	@XmlAttribute
	private Long ID;
	
	@OneToOne
	@JoinColumn(name = "Category")
	@XmlAttribute
	private AdvertCategory category;
	
	@OneToOne
	@JoinColumn(name = "Owner")
	@XmlAttribute
	private User owner;
	
	@Column(name = "Title", nullable = false)
	@XmlAttribute
	private String title;
	
	@Column(name = "Description", nullable = false)
	@XmlAttribute
	private String description;
	
	@Lob
	@Column(name = "Image", nullable = false)
	@XmlAttribute
	private byte[] image;
	
	@Column(name = "Cost", nullable = false)
	@XmlAttribute
	private float cost;
	
	public Advert() {
		super();
	}
	
	public Advert(Long ID, AdvertCategory category, User owner, String title, String description, byte[] image, float cost) {
		super();
		this.ID = ID;
		this.category = category;
		this.owner = owner;
		this.title = title;
		this.description = description;
		this.image = image;
		this.cost = cost;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public AdvertCategory getCategory() {
		return category;
	}

	public void setCategory(AdvertCategory category) {
		this.category = category;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
}
