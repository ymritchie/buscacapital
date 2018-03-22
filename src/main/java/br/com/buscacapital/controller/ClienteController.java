package br.com.buscacapital.controller;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.apache.log4j.Logger;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.buscacapital.bo.ClienteBO;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.util.BuscaCapitalUtils;
import br.com.buscacapital.util.Mensagens;

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
	
	private static String FW_MANTER_CLIENTE = "/admin/manter-cliente.xhtml?faces-redirect=true";
	
	private boolean pesquisaCliente;
	
	@Autowired
	private ClienteBO clienteBO;
	
	private Cliente cliente;
	
	private String cep;
	
	
	public String iniciarCliente() {
		this.pesquisaCliente = true;
		return FW_MANTER_CLIENTE;
	}
	
	/**
	 * 
	 * @param cep
	 */
	public void pesquisarEnderecoPorCep() {
		try {
			this.cep = this.cep.replaceAll("[^0-9]", "");
			URL urlConsultarCep = new URL("http://api.postmon.com.br/v1/cep/" + this.cep);
			/*
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.30.0.1", 9090));
			
			HttpURLConnection connection = (HttpURLConnection) urlConsultarCep.openConnection(proxy);
			*/
			HttpURLConnection connection = (HttpURLConnection) urlConsultarCep.openConnection();
			connection.setConnectTimeout(15000);
			connection.connect();
			
			String respostaJson = BuscaCapitalUtils.inputStreamToString(connection.getInputStream());
			
			if (!respostaJson.isEmpty()) {
				JSONObject  enderecoObj = new JSONObject(respostaJson);
				System.out.println(enderecoObj.getString("logradouro"));
				System.out.println(enderecoObj.getString("cidade"));
				
			}
			
			System.out.println(respostaJson);
			
			connection.disconnect();
			
		} catch (Exception e) {
			log.error("Erro ao consultar CEP: " + cep + ", " + e.getMessage());
			Mensagens.addMsgErro("Erro ao consultar CEP: " + cep + ", " + e.getMessage());
		}
	}
	
	public boolean isPesquisaCliente() {
		return pesquisaCliente;
	}

	public void setPesquisaCliente(boolean pesquisaCliente) {
		this.pesquisaCliente = pesquisaCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
}
