package br.com.buscacapital.controller;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.buscacapital.bo.ClienteBO;
import br.com.buscacapital.enumerator.EstadosBrasil;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.model.ClientePf;
import br.com.buscacapital.model.ClientePj;
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
	
	private ClientePf clientePf;
	
	private ClientePj clientePj;
	
	private List<Cliente> listaClientes;
	
	private String cep;
	
	private List<SelectItem> listaEstados;
	
	
	public String iniciarCliente() {
		this.pesquisaCliente = true;
		this.listaEstados = EstadosBrasil.listaElementoEnum();
		
		this.listaClientes = new ArrayList<Cliente>(this.clienteBO.listarTodos());
		return FW_MANTER_CLIENTE;
	}
	
	/**
	 * 
	 * @param cep
	 */
	public void pesquisarEnderecoPorCep() {
		try {
			this.cep = this.cliente.getEnderecoCep().replaceAll("[^0-9]", "");
			URL urlConsultarCep = new URL("http://api.postmon.com.br/v1/cep/" + this.cep);
			
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.30.0.1", 9090));
			
			HttpURLConnection connection = (HttpURLConnection) urlConsultarCep.openConnection(proxy);
			
			//HttpURLConnection connection = (HttpURLConnection) urlConsultarCep.openConnection();
			connection.setConnectTimeout(15000);
			connection.connect();
			
			String respostaJson = BuscaCapitalUtils.inputStreamToString(connection.getInputStream());
			
			if (!respostaJson.isEmpty()) {
				JSONObject  enderecoObj = new JSONObject(respostaJson);
				this.cliente.setEnderecoBairro(enderecoObj.getString("bairro"));
				this.cliente.setEnderecoCidade(enderecoObj.getString("cidade"));
				this.cliente.setEnderecoLogradouro(enderecoObj.getString("logradouro"));
				this.cliente.setEnderecoUf(enderecoObj.getString("estado"));
			}
			
			System.out.println(respostaJson);
			
			connection.disconnect();
			
		} catch (Exception e) {
			log.error("Erro ao consultar CEP: " + cep + ", " + e.getMessage());
			Mensagens.addMsgInfo("CEP não encontrado!");
		}
	}
	
	/**
	 * @since 22/03/2018
	 */
	public void incluirCliente() {
		this.cliente = new Cliente();
		this.pesquisaCliente = false;
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

	public ClientePf getClientePf() {
		return clientePf;
	}

	public void setClientePf(ClientePf clientePf) {
		this.clientePf = clientePf;
	}

	public ClientePj getClientePj() {
		return clientePj;
	}

	public void setClientePj(ClientePj clientePj) {
		this.clientePj = clientePj;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<SelectItem> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<SelectItem> listaEstados) {
		this.listaEstados = listaEstados;
	}

	
	
}
