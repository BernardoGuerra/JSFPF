package com.controlador;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.dto.UsuarioDTO;

import com.exception.ServiciosException;
import com.negocio.GestionUsuarioBean;


@Named("eliminarusuario")
@ConversationScoped
public class EliminarUsuario implements Serializable{

	private static final long serialVersionUID = 1L;


	@EJB
	private GestionUsuarioBean usuarioEJB;
	

	
	private String nombre;
	
	private String apellido;
	
	private String documentoCategoria;
	
	private String documento;

	private String email;
	
	private String estadoUsuario;
	
	private String emailBusqueda;

	
	


	public void initConversation(){
	    if (!FacesContext.getCurrentInstance().isPostback() ) {
	    	FacesContext fc = FacesContext.getCurrentInstance();	
			if(!fc.getExternalContext().getRequestParameterMap().isEmpty()) {
				String email= fc.getExternalContext().getRequestParameterMap().get("userEmail");
				System.out.println("HOLA ENTTRE " + email );
				try {
					UsuarioDTO us = usuarioEJB.obtenerUsuarioEmail(email);
					this.nombre = us.getNombre();
					this.apellido = us.getApellido();
					this.documentoCategoria = us.getDocumentoCategoria();
					this.documento = us.getDocumento();
					this.email = us.getEmail();
					this.estadoUsuario = us.getEstadoUsuario();
				
				} catch (ServiciosException e) {
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "ERROR");
					context.addMessage("", message);
				}
			}
	    }
	}
	
	
	public String getEmailBusqueda() {
		return emailBusqueda;
	}


	public void setEmailBusqueda(String emailBusqueda) {
		this.emailBusqueda = emailBusqueda;
	}


	public GestionUsuarioBean getUsuarioEJB() {
		return usuarioEJB;
	}

	public void setUsuarioEJB(GestionUsuarioBean usuarioEJB) {
		this.usuarioEJB = usuarioEJB;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getDocumentoCategoria() {
		return documentoCategoria;
	}

	public void setDocumentoCategoria(String documentoCategoria) {
		this.documentoCategoria = documentoCategoria;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstadoUsuario() {
		return estadoUsuario;
	}

	public void setEstadoUsuario(String estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}
	
	
	public String busquedaPorCorreo() {
		return "/eliminarusuario.xhtml?faces-redirect=true&userEmail=" + this.getEmailBusqueda();
	}
	
	
	public String eliminar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		String redirect = "/pages/eliminarusuario.xhtml?faces-redirect=true";
		if(!this.email.isEmpty()) {
			try {
				usuarioEJB.bajaLogicaUsuario(this.email);;
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "EL Usuario fue Deshabilitado", "OK");
				context.addMessage("", message);
				context.getExternalContext().getFlash().setKeepMessages(true);
				        
			} catch (ServiciosException e) {
				
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "ERROR");
				context.addMessage("", message);
				context.getExternalContext().getFlash().setKeepMessages(true);
		        return " ";
			}
			//POST-Redirect-GET 
			//return redirect;
		}else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Debe buscar un usuario");
			context.addMessage("", message);
			context.getExternalContext().getFlash().setKeepMessages(true);
			return " ";
		}
		
		return redirect;
	}
}
