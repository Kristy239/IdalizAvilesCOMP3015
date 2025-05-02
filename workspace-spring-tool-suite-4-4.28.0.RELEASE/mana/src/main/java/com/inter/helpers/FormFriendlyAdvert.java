
package com.inter.helpers;

import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

import com.inter.model.Advert;
import com.inter.model.AdvertCategory;
import com.inter.model.User;

public class FormFriendlyAdvert {
	private Long ID;
	private String title;
	private String description;
	private MultipartFile image;
	private String imageBase64;
	private byte[] imageBytes;
	private String imageContentType;
	private float cost;
	private User owner;
	private AdvertCategory category;

	public FormFriendlyAdvert() {
		super();
	}

	public FormFriendlyAdvert(Advert advert) {
		this.setID(advert.getID());
		this.setTitle(advert.getTitle());
		this.setDescription(advert.getDescription());
		this.setImageBytes(advert.getImageContentType(), advert.getImage());
		this.setCost(advert.getCost());
		this.setOwner(advert.getOwner());
		this.setCategory(advert.getCategory());
	}

	public Advert toAdvert() {
		return new Advert(this.getID(), this.getTitle(), this.getDescription(), this.getCost(), this.getImageBytes(),
				this.getImageContentType(), this.getOwner(), this.getCategory());
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
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

	public MultipartFile getImage() {
		return this.image;
	}

	public String getImageBase64() {
		return this.imageBase64;
	}

	public byte[] getImageBytes() {
		return this.imageBytes;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImage(MultipartFile multipartFile) {
		this.image = multipartFile;

		if (multipartFile.isEmpty()) {
			this.setImageBytes("", new byte[0]);
		} else {
			try {
				this.setImageBytes(multipartFile.getContentType(), multipartFile.getBytes());
			} catch (IOException e) {
				// Ignore error
				System.err.println("Could not convert multipartFile to image");
			}
		}
	}

	public void setImageBase64(String image) {
		this.imageBase64 = image;
		// Regardless of the format of the image, split the string by ";base64,"
		String[] parts = image.split(";base64,");

		// Only decode bytes for the last portion
		this.imageBytes = Base64.getDecoder().decode(parts[parts.length - 1]);

		// Take the first part of what was split, and split it again using "data:"
		parts = parts[0].split("data:");

		// Take the last part again, this time to extract the image content type
		this.imageContentType = parts[parts.length - 1];
	}

	public void setImageBytes(String contentType, byte[] image) {
		this.imageContentType = contentType;
		this.imageBytes = image;

		// Encode the image to generate the imageBase64 string
		this.imageBase64 = Base64.getEncoder().encodeToString(image);

		// Don't forget to add the prefix with the expected file format
		this.imageBase64 = "data:" + contentType + ";base64," + this.imageBase64;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
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
