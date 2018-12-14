package com.example.demo.dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 7944361995072131358L;
	@Size(min = 2, max = 30)
	private String name;
	@Column(unique = true)
	@Email
	private String email;
	@Column(unique = true)
	private String phone;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
