package com.controlador;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.dto.FenomenoDTO;
import com.exception.ServiciosException;
import com.negocio.GestionFenomenoBean;


@Named("eliminarfenomeno")
@ConversationScoped
public class EliminarFenomeno implements Serializable{

	private static final long serialVersionUID = 1L;


	@EJB
	private GestionFenomenoBean fenomenoEJB;
	

	
	private String codigo;
	
	private String nombre;
	
	private String descripcion;
	
	private String telefono;

	
	public void initConversation(){
	    if (!FacesContext.getCurrentInstance().isPostback() ) {
	    	FacesContext fc = FacesContext.getCurrentInstance();	
			if(!fc.getExternalContext().getRequestParameterMap().isEmpty()) {
				String codigo= fc.getExternalContext().getRequestParameterMap().get("codigo");
				System.out.println("HOLA ENTTRE " + codigo );
				try {
					FenomenoDTO fs = (FenomenoDTO) fenomenoEJB.obtenerFenomenos();
					this.codigo = fs.getCodigo();
					this.nombre = fs.getNombre();
					this.descripcion = fs.getDescripcion();
					this.telefono = fs.getTelefono();
									
				} catch (ServiciosException e) {
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "ERROR");
					context.addMessage("", message);
				}
			}
	    }
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String busquedaPorCodigo() {
		return "/eliminarFenomeno.xhtml?faces-redirect=true&codigo=" + this.codigo;
	}
	
	
	public String eliminar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		String redirect = "/pages/eliminarFenomeno.xhtml?faces-redirect=true";
		if(!this.codigo.isEmpty()) {
			try {
				fenomenoEJB.bajaFenomeno(this.codigo);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "EL Fenomeno fue dado de Baja", "OK");
				context.addMessage("", message);
				context.getExternalContext().getFlash().setKeepMessages(true);
				        
			} catch (ServiciosException e) {
				
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "ERROR");
				context.addMessage("", message);
				context.getExternalContext().getFlash().setKeepMessages(true);
		        return " ";
			}
			
		}else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Debe buscar un codigo de Fenomeno");
			context.addMessage("", message);
			context.getExternalContext().getFlash().setKeepMessages(true);
			return " ";
		}
		
		return redirect;
	}
}
