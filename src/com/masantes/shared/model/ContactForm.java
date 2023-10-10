package com.masantes.shared.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContactForm implements Serializable
{
	private static final long serialVersionUID = 2813534119695449939L;
	
	@Size(min=3, max=40)
	@NotNull
	private String name;
	@Size(min=5, max=20)
	@NotNull
	private String email;
	
	private String message;
	
	public ContactForm(){
	}
	
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}
