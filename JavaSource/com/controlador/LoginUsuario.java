package com.controlador;


import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.dto.UsuarioDTO;
import com.enumerados.EnumCategoriaUsuario;
import com.enumerados.EnumEstadoUsuario;
import com.exception.ServiciosException;
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
		String redirect = " ";

		try {
			u = persistenciaUsuario.validarUsuario(this.email, this.password);
		} catch (NoSuchAlgorithmException | ServiciosException e) {
			e.printStackTrace();
		}
		
		if(u != null && u.getEstadoUsuario().equals(EnumEstadoUsuario.HABILITADO.toString())) {
			
			fc.getExternalContext().getRequestMap().put("usuario", u);
			if(u.getRol().equals(EnumCategoriaUsuario.ADMINISTRADOR.toString())) redirect = "";
			if(u.getRol().equals(EnumCategoriaUsuario.EXPERTO.toString())) redirect = "";
			if(u.getRol().equals(EnumCategoriaUsuario.VOLUNTARIO.toString())) redirect = "";
			
		}
		
		return redirect;
	}
	
	
}
