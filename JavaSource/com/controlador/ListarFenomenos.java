package com.controlador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import com.dto.FenomenoDTO;
import com.exception.ServiciosException;
import com.negocio.GestionFenomenoBean;



@Named("listarfenomenos")
@ConversationScoped	
public class ListarFenomenos implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private GestionFenomenoBean servicioFenomeno;

	
	private List<FenomenoDTO> listaFenomenos = new ArrayList<FenomenoDTO>();
	
	@PostConstruct
	public void init()  {
		try {
			listaFenomenos = servicioFenomeno.obtenerFenomenos();
		} catch (ServiciosException e) {
			e.printStackTrace();
		}
		for(FenomenoDTO f : listaFenomenos) {
			System.out.println("Este son los fenomenos: " + f.getNombre());
		}
	}

	public List<FenomenoDTO> getListaFenomenos() {
		return listaFenomenos;
	}
	public void setListaFenomenos(List<FenomenoDTO> listaFenomenos) {
		this.listaFenomenos = listaFenomenos;
	}
	
	
}
