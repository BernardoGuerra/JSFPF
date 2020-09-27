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



@Named("altafenomeno")
@ConversationScoped	
public class AltaFenomeno implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private GestionFenomenoBean fenomenoEJB;
	
	@Size(min=2,max=40, message = "El codigo debe contener entre 2 y 10 caracteres.")
	private String codigo;
	
	@Size(min=2,max=40, message = "El nombre debe contener entre 2 y 40 caracteres.")
	private String nombre;
	
	@Size(min=2,max=50, message = "La descripcion debe contener entre 2 y 50 caracteres.")
	private String descripcion;

	@Size(min=3,max=3, message = "El Telefono debe contener entre 3 caracteres.")
	private String telefono;
	
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
	public String crearFenomeno() {
		
		FenomenoDTO fenomeno = new FenomenoDTO();
		
		fenomeno.setCodigo(this.codigo);
		fenomeno.setDescripcion(this.descripcion);
		fenomeno.setNombre(this.nombre);
		fenomeno.setTelefono(this.telefono);
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			fenomenoEJB.agregarFenomeno(fenomeno);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "EL Fenomeno fue creado", "OK");
			context.addMessage("", message);
			context.getExternalContext().getFlash().setKeepMessages(true);
			
	      
	        
		} catch (ServiciosException | NoSuchAlgorithmException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "ERROR");
			context.addMessage("", message);
			context.getExternalContext().getFlash().setKeepMessages(true);
	        return " ";
			
			
		}
		
		//POST-Redirect-GET 
		return "/pages/altaFenomeno.xhtml?faces-redirect=true?i=1";

	}
	

	
}
