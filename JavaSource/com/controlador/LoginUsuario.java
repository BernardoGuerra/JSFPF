package com.controlador;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Named("loginusuario")
@SessionScoped
public class LoginUsuario {

	@Email
	private String email;
	
	@Size(min=2,max=40, message = "El nombre debe contener entre 2 y 40 caracteres.")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String validarUsuario() {
		return "";
	}
	
	
}
