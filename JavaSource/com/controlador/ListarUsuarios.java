package com.controlador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import com.dto.UsuarioDTO;
import com.exception.ServiciosException;
import com.negocio.GestionUsuarioBean;



@Named("listarusuarios")
@ConversationScoped	
public class ListarUsuarios implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private GestionUsuarioBean servicioUsuario;

	
	private List<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
	
	@PostConstruct
	public void init()  {
		try {
			listaUsuarios = servicioUsuario.obtenerUsuarios();
		} catch (ServiciosException e) {
			e.printStackTrace();
		}
		for(UsuarioDTO u : listaUsuarios) {
			System.out.println("Este es mi NOMBRE: " + u.getNombre());
		}
	}

	public List<UsuarioDTO> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<UsuarioDTO> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
	
}
