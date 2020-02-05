package com.workwaves.app.api.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
	
	
	@NotNull(message="First name cannot be null")
	@Size(min=2, message="First name must be greater than 2 characters")
	private String firstName;
	@NotNull(message="Last name cannot be null")
	@Size(min=2, message="Last name must be greater than 2 characters")
	private String lastName;
	@NotNull(message="password name cannot be null")
	@Size(min=4, max=25, message="password must be greater than 4 and less than 25 characters")
	private String password;
	@NotNull
	@Email
	private String email;
	
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	

}
