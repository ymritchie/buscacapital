package br.com.buscacapital.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.buscacapital.bo.ClienteBO;

/**
 * Controlador das requisições da tela manter-cliente
 * 
 * @author Yanisley Mora Ritchie
 *
 */
@Scope("session")
@Component("clienteController")
public class ClienteController {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(ClienteController.class);
	
	private static String FW_MANTER_CLIENTE = "/manter-cliente.xhtml?faces-redirect=true";
	
	private boolean pesquisaCliente;
	
	@Autowired
	private ClienteBO clienteBO;
	
	
	public String iniciarCliente() {
		this.pesquisaCliente = true;
		return FW_MANTER_CLIENTE;
	}


	public boolean isPesquisaCliente() {
		return pesquisaCliente;
	}


	public void setPesquisaCliente(boolean pesquisaCliente) {
		this.pesquisaCliente = pesquisaCliente;
	}
	
	
	
	
}
