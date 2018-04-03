package br.com.buscacapital.controller;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.buscacapital.bo.ClienteBO;
import br.com.buscacapital.bo.UsuarioBO;
import br.com.buscacapital.contex.SessionContext;
import br.com.buscacapital.enumerator.EstadosBrasil;
import br.com.buscacapital.enumerator.TextosFixos;
import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.model.ClientePf;
import br.com.buscacapital.model.ClientePj;
import br.com.buscacapital.model.Usuario;
import br.com.buscacapital.util.BCUtils;
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
	
	@Autowired
	private UsuarioBO usuarioBO;
	
	private Cliente cliente;
	
	private ClientePf clientePf;
	
	private ClientePj clientePj;
	
	private List<Cliente> listaClientes;
	
	private List<Cliente> listaFiltrada;
	
	private String cep;
	
	private List<SelectItem> listaEstados;
	
	private boolean tipoClientePessoaFisica;
	
	private Date dataMinimaCadastro;
	
	private Date dataMinimaIdade;
	
	private Usuario usuario;
	
	private String termoDeUso;
	
	private boolean concordarTermo;
	
	private boolean editarCliente;
	
	
	public String iniciarCliente() {
		this.editarCliente = false;
		this.pesquisaCliente = true;
		this.listaEstados = EstadosBrasil.listaElementoEnum();
		
		this.concordarTermo = false;
		
		this.listaClientes = new ArrayList<Cliente>(this.clienteBO.listarTodos());
		this.termoDeUso = TextosFixos.TERMO_DE_USO.getTexto();
		return FW_MANTER_CLIENTE;
	}
	
	/**
	 * 
	 * @param cep
	 */
	public void pesquisarEnderecoPorCep() {
		try {
			this.cep = this.tipoClientePessoaFisica == true ? this.clientePf.getEnderecoCep().replaceAll("[^0-9]", "") : this.clientePj.getEnderecoCep().replaceAll("[^0-9]", "");
			
			URL urlConsultarCep = new URL("http://api.postmon.com.br/v1/cep/" + this.cep);
			
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.30.0.1", 9090));
			
			HttpURLConnection connection = (HttpURLConnection) urlConsultarCep.openConnection(proxy);
			
			//HttpURLConnection connection = (HttpURLConnection) urlConsultarCep.openConnection();
			connection.setConnectTimeout(15000);
			connection.connect();
			
			String respostaJson = BCUtils.inputStreamToString(connection.getInputStream());
			
			if (!respostaJson.isEmpty()) {
				JSONObject  enderecoObj = new JSONObject(respostaJson);
				if (this.tipoClientePessoaFisica) {
					this.clientePf.setEnderecoBairro(enderecoObj.getString("bairro"));
					this.clientePf.setEnderecoCidade(enderecoObj.getString("cidade"));
					this.clientePf.setEnderecoLogradouro(enderecoObj.getString("logradouro"));
					this.clientePf.setEnderecoUf(enderecoObj.getString("estado"));
				} else {
					this.clientePj.setEnderecoBairro(enderecoObj.getString("bairro"));
					this.clientePj.setEnderecoCidade(enderecoObj.getString("cidade"));
					this.clientePj.setEnderecoLogradouro(enderecoObj.getString("logradouro"));
					this.clientePj.setEnderecoUf(enderecoObj.getString("estado"));
				}
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
		this.editarCliente = false;
		
		this.clientePf = new ClientePf();
		this.usuario = new Usuario();
		this.clientePf.setUsuario(this.usuario);
		this.clientePf.setTipoCliente("PF");
		this.pesquisaCliente = false;
		this.tipoClientePessoaFisica = true;
		
		this.concordarTermo = false;
	}
	
	/**
	 * Instancia o Cliente correto conforme a opção escolhida na tela (Pessoa Física ou Jurídica)
	 * @since 230/03/2018
	 */
	public void trocarTipoPessoa() {
		this.tipoClientePessoaFisica = this.tipoClientePessoaFisica == true ? false : true;
		
		if (this.tipoClientePessoaFisica) {
			this.clientePf = new ClientePf();
			this.clientePf.setTipoCliente("PF");
			this.clientePf.setUsuario(this.usuario);
			this.clientePj = null;
			this.tipoClientePessoaFisica = true;
		} else {
			this.clientePj = new ClientePj();
			this.clientePj.setTipoCliente("PJ");
			this.clientePj.setUsuario(this.usuario);
			this.clientePf = null;
			this.tipoClientePessoaFisica = false;
		}
		
		this.concordarTermo = false;
	}
	
	/**
	 * Salva o cliente antendendo as características de PF ou PJ
	 */
	public void salvarCliente() {
		try {
			if (!this.isConcordarTermo() && !this.editarCliente) {
				throw new BuscaCapitalException("Deve aceitar os termos de uso antes de continuar.");
			}
			
			if (!this.editarCliente) {
				this.usuario.setAdministrador(false);
				this.usuario.setAtivo(true);
			}
			
			if (this.tipoClientePessoaFisica) {
				if (this.clientePf.getTelefone().replaceAll("[^0-9]", "").length() < 8) {
					throw new BuscaCapitalException("O telefone informado não é válido.");
				}
				
				if (!BCUtils.isCpfValido(this.clientePf.getCpf().replaceAll("[^0-9]", ""))) {
					throw new BuscaCapitalException("O CPF: " + this.clientePf.getCpf() + " não corresponde com um CPF válido.");
				}
				
				if (this.clientePf.getDataNascimento().after(this.getDataMinimaIdade())) {
					throw new BuscaCapitalException("A data de nascimento: " + BCUtils.formataDataPorPadrao(this.clientePf.getDataNascimento()) + " corresponde a um menor de idade.");
				}
				
				if (!this.editarCliente) {
					this.usuario.setEmail(this.clientePf.getEmail());
					this.usuario.setTelefone(this.clientePf.getTelefone());
					this.usuario.setNome(this.clientePf.getNome());
					this.usuario.setSobrenome(this.clientePf.getSobrenome());
					this.usuario.setDataNascimento(this.clientePf.getDataNascimento());
				}
				
				
				this.clientePf.setCpf(this.clientePf.getCpf().replaceAll("[^0-9]", ""));
				
				this.clienteBO.salvarCliente(this.clientePf);
				
			} else {
				
				if (this.clientePj.getTelefone().replaceAll("[^0-9]", "").length() < 8) {
					throw new BuscaCapitalException("O telefone informado não é válido.");
				}
				
				if (!BCUtils.isCnpjValido(this.clientePj.getCnpj().replaceAll("[^0-9]", ""))) {
					throw new BuscaCapitalException("O CNPJ: "  + this.clientePj.getCnpj() + " não corresponde com um CNPJ válido.");
				}
				
				if (this.clientePj.getDataInscricao().after(this.getDataMinimaCadastro())) {
					throw new BuscaCapitalException("A data de incrição: " + BCUtils.formataDataPorPadrao(this.clientePj.getDataInscricao()) + " é posterior à data atual.");
				}
				
				if (!this.editarCliente) {
					this.usuario.setEmail(this.clientePj.getEmail());
					this.usuario.setTelefone(this.clientePj.getTelefone());
					this.usuario.setNome(this.clientePj.getNomeFantasia());
					this.usuario.setSobrenome(this.clientePj.getRazaoSocial());
					this.usuario.setDataNascimento(this.clientePj.getDataInscricao());
				}
				
				this.clientePj.setCnpj(this.clientePj.getCnpj().replaceAll("[^0-9]", ""));
				
				this.clienteBO.salvarCliente(this.clientePj);
				
			}
			
			Mensagens.addMsgInfo("Cliente salvo com sucesso!");
			this.pesquisaCliente = true;
			this.editarCliente = false;
			this.listaClientes = new ArrayList<Cliente>(this.clienteBO.listarTodos());
		} catch (Exception e) {
			log.error(e);
			Mensagens.addMsgErro(e.getMessage());
		}
	}
	
	/**
	 * Cancela a inclusão de cliente e retorna á tela de pesquisa
	 */
	public void cancelarCadastroCliente() {
		this.pesquisaCliente = true;
		this.editarCliente = false;
		this.listaClientes = new ArrayList<Cliente>(this.clienteBO.listarTodos());
	}
	
	
	/**
	 * 
	 * @param cliente
	 */
	public void editarCliente(Cliente cliente) {
		this.editarCliente = true;
		
		//this.cliente = cliente;
		if (cliente.getTipoCliente().equalsIgnoreCase("PF")) {
			this.clientePf = (ClientePf) cliente;
			this.tipoClientePessoaFisica = true;
		} else {
			this.clientePj = (ClientePj) cliente;
			this.tipoClientePessoaFisica = false;
		}
		
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

	public boolean isTipoClientePessoaFisica() {
		return tipoClientePessoaFisica;
	}

	public void setTipoClientePessoaFisica(boolean tipoClientePessoaFisica) {
		this.tipoClientePessoaFisica = tipoClientePessoaFisica;
	}
	
	public Date getDataMinimaCadastro() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
		
		this.dataMinimaCadastro = cal.getTime();
		
		return dataMinimaCadastro;
	}

	public void setDataMinimaCadastro(Date dataMinimaCadastro) {
		this.dataMinimaCadastro = dataMinimaCadastro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTermoDeUso() {
		return termoDeUso;
	}

	public void setTermoDeUso(String termoDeUso) {
		this.termoDeUso = termoDeUso;
	}

	public Date getDataMinimaIdade() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 18);
		
		this.dataMinimaIdade = cal.getTime();
		
		return dataMinimaIdade;
	}

	public void setDataMinimaIdade(Date dataMinimaIdade) {
		this.dataMinimaIdade = dataMinimaIdade;
	}

	public boolean isConcordarTermo() {
		return concordarTermo;
	}

	public void setConcordarTermo(boolean concordarTermo) {
		this.concordarTermo = concordarTermo;
	}
	
	public Cliente getClienteSessao() {
		return (Cliente) SessionContext.getInstance().getClienteSessao();
	}

	public List<Cliente> getListaFiltrada() {
		return listaFiltrada;
	}

	public void setListaFiltrada(List<Cliente> listaFiltrada) {
		this.listaFiltrada = listaFiltrada;
	}

	public boolean isEditarCliente() {
		return editarCliente;
	}

	public void setEditarCliente(boolean editarCliente) {
		this.editarCliente = editarCliente;
	}
	
}
