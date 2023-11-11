package com.ecommerce.web.UserEntity;

import java.util.Set;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;

@Entity(name = "TBL_USER")
public class User {

	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	private String username;
	private String password;
	private Boolean isActive;

	@ManyToMany
	private Set<Role> Roles;

	@PrePersist
	public void initInsertData() {
		this.isActive = true;
	}

	public User() {
		super();
	}

	public User(int id, String username, String password, Boolean isActive, Set<Role> roles) {
		super();
		Id = id;
		this.username = username;
		this.password = password;
		this.isActive = isActive;
		Roles = roles;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<Role> getRoles() {
		return Roles;
	}

	public void setRoles(Set<Role> roles) {
		Roles = roles;
	}

}
