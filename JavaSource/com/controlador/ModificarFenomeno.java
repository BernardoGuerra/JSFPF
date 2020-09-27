package com.controlador;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.Size;

import com.dto.FenomenoDTO;
import com.exception.ServiciosException;
import com.negocio.GestionFenomenoBean;

@Named("modificarfenomeno")
@ConversationScoped
public class ModificarFenomeno implements Serializable{

	private static final long serialVersionUID = 1L;


	@EJB
	private GestionFenomenoBean fenomenoEJB;
	
	@Size(min=2,max=40, message = "El codigo debe contener entre 2 y 40 caracteres.")
	private String codigo;
	
	@Size(min=2,max=40, message = "El nombre debe contener entre 2 y 40 caracteres.")
	private String nombre;
	
	@Size(min=2,max=40, message = "La descripcion debe contener entre 2 y 40 caracteres.")
	private String descripcion;

	@Size(min=3,max=3, message = "El telefono debe contener solo 3 caracteres.")
	private String telefono;
	
	
	
	public void initConversation(){
	    if (!FacesContext.getCurrentInstance().isPostback() ) {
	    	FacesContext fc = FacesContext.getCurrentInstance();	
			if(!fc.getExternalContext().getRequestParameterMap().isEmpty()) {
				String codigo= fc.getExternalContext().getRequestParameterMap().get("codigo");
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
	

	public GestionFenomenoBean getFenomenoEJB() {
		return fenomenoEJB;
	}

	public void setFenomenoEJB(GestionFenomenoBean fenomenoEJB) {
		this.fenomenoEJB = fenomenoEJB;
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
		return "/modificarFenomeno.xhtml?faces-redirect=true&codigo=" + this.codigo;
	}
	
	public String modificarFenomeno() {
		
		FenomenoDTO fenomeno = new FenomenoDTO();
		fenomeno.setCodigo(this.getCodigo());
		fenomeno.setNombre(this.getNombre());
		fenomeno.setDescripcion(this.getDescripcion());
		fenomeno.setTelefono(this.getTelefono());
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			fenomenoEJB.modificarFenomeno(fenomeno);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "EL Fenomeno fue actualizado", "OK");
			context.addMessage("", message);
			context.getExternalContext().getFlash().setKeepMessages(true);
			        
		} catch (ServiciosException | NoSuchAlgorithmException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "ERROR");
			context.addMessage("", message);
			context.getExternalContext().getFlash().setKeepMessages(true);
	        return " ";
		}
		//POST-Redirect-GET 
		return "/pages/modificarFenomeno.xhtml?faces-redirect=true";
		
	}
}
