package com.controlador;


import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.dto.UsuarioDTO;
import com.negocio.GestionUsuarioBean;

@Named("loginusuario")
@SessionScoped
public class LoginUsuario implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EJB
	private GestionUsuarioBean persistenciaUsuario;


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
		UsuarioDTO u = new UsuarioDTO();
		
		
		
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getExternalContext().getRequestMap().put("Usuairo", u);
		return "";
	}
	
	
}
