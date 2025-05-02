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

	@Column(name = "Title", nullable = false)
	@XmlAttribute
	private String title;

	@Column(name = "Description", nullable = false)
	@XmlAttribute
	private String description;

	@Column(name = "Cost", nullable = false)
	@XmlAttribute
	private float cost;

	@Lob
	@Column(name = "Image", nullable = true)
	@XmlAttribute
	private byte[] image;

	@Column(name = "ImageContentType", nullable = true)
	@XmlAttribute
	private String imageContentType;

	@OneToOne
	@JoinColumn(name = "Owner", nullable = false)
	@XmlAttribute
	private User owner;

	@OneToOne
	@JoinColumn(name = "Category", nullable = true)
	@XmlAttribute
	private AdvertCategory category;

	public Advert() {
		super();
	}

	public Advert(Long ID, String title, String description, float cost, byte[] image, String imageContentType, User owner, AdvertCategory category) {
		super();
		this.ID = ID;
		this.title = title;
		this.description = description;
		this.cost = cost;
		this.image = image;
		this.imageContentType = imageContentType;
		this.owner = owner;
		this.category = category;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
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

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public AdvertCategory getCategory() {
		return category;
	}

	public void setCategory(AdvertCategory category) {
		this.category = category;
	}
}
