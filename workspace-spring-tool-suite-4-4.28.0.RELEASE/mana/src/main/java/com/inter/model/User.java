package com.inter.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	@XmlAttribute
	private Long ID;

	@Column(name = "Username", nullable = false)
	@XmlAttribute
	private String username;

	@Column(name = "Name", nullable = false)
	@XmlAttribute
	private String name;

	@Column(name = "LastName", nullable = false)
	@XmlAttribute
	private String lastName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	@Column(name = "DateOfBirth", nullable = false)
	@XmlAttribute
	private Date dateOfBirth;

	@Column(name = "Password", nullable = false)
	@XmlAttribute
	private String password;

	@Email
	@Column(name = "Email", nullable = false)
	@XmlAttribute
	private String email;

	@Column(name = "PhoneNumber", nullable = true)
	@XmlAttribute
	private String phoneNumber;

	public User() {
		super();
	}

	public User(Long ID, String username, String name, String lastName, Date dateOfBirth, String password, String email,
			String phoneNumber) {
		super();
		this.ID = ID;
		this.username = username;
		this.name = name;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Give all users the same Granted Authority of ROLE_USER
		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}
}
