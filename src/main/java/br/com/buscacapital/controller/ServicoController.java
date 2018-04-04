package br.com.buscacapital.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.buscacapital.bo.CategoriaBO;
import br.com.buscacapital.bo.ServicoBO;
import br.com.buscacapital.bo.SubCategoriaBO;
import br.com.buscacapital.contex.SessionContext;
import br.com.buscacapital.model.Categoria;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.model.Servico;
import br.com.buscacapital.model.SubCategoria;
import br.com.buscacapital.util.Mensagens;

/**
 * Controlador das requisições da tela manter-servicos
 * 
 * @author Yanisley Mora Ritchie
 *
 */

@Scope("session")
@Component("servicoController")
public class ServicoController {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(ServicoController.class);
	
	private static String FW_MANTER_SERVICO = "/admin/manter-servico.xhtml?faces-redirect=true";
	
	private boolean pesquisaServico;
	
	private boolean clienteLogado;
	
	@Autowired
	private ServicoBO servicoBO;
	
	@Autowired
	private CategoriaBO categoriaBO;
	
	@Autowired
	private SubCategoriaBO subCategoriaBO;
	
	private Servico servico;
	
	private List<Servico> listaServico;
	
	private List<Servico> listaFiltrada;
	
	private List<Categoria> listaCategoria;
	
	private List<SubCategoria> listaSubCategoria;
	
	private Cliente cliente;
	
	private StreamedContent imgServico;
	
	/**
	 * Inicia a tela de de manter serviços
	 * @return
	 */
	public String iniciarServico() {
		this.pesquisaServico = true;
		this.cliente = (Cliente) SessionContext.getInstance().getClienteSessao();
		
		this.listaServico = new ArrayList<Servico>(this.servicoBO.listarPorCliente(this.cliente));
		this.listaCategoria = new ArrayList<Categoria>(this.categoriaBO.listarTodos());
		this.listaSubCategoria = new ArrayList<SubCategoria>(this.subCategoriaBO.listarTodos());
		return FW_MANTER_SERVICO;
	}
	
	/**
	 * 
	 */
	public void incluirNovoServico() {
		this.servico = new Servico();
		this.imgServico = null;
		this.pesquisaServico = false;
	}
	
	/**
	 * 
	 */
	public void salvarServico() {
		
	}
	
	/**
	 * 
	 * @param servico
	 */
	public void excluirServico(Servico servico) {
		
	}
	
	/**
	 * 
	 */
	public void cancelarCadastroServico() {
		this.pesquisaServico = true;
		this.listaServico = new ArrayList<Servico>(this.servicoBO.listarPorCliente(this.cliente));
	}
	
	/**
	 * 
	 * @param servico
	 */
	public void editarServico(Servico servico) {
		
	}
	
	/**
	 * 
	 * @param event
	 */
	public void processarUploadImagem (FileUploadEvent event){
		try {
			UploadedFile arquivoUpoad = event.getFile();
			this.imgServico = new DefaultStreamedContent(arquivoUpoad.getInputstream(), arquivoUpoad.getContentType(), arquivoUpoad.getFileName());
		} catch (Exception e)  {
			log.error(e);
			Mensagens.addMsgErro("Não foi possível anexar o arquivo " + event.getFile().getFileName());
		}
	}
	
	public boolean isPesquisaServico() {
		return pesquisaServico;
	}

	public void setPesquisaServico(boolean pesquisaServico) {
		this.pesquisaServico = pesquisaServico;
	}

	public boolean isClienteLogado() {
		return clienteLogado;
	}

	public void setClienteLogado(boolean clienteLogado) {
		this.clienteLogado = clienteLogado;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public List<Servico> getListaServico() {
		return listaServico;
	}

	public void setListaServico(List<Servico> listaServico) {
		this.listaServico = listaServico;
	}

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public List<SubCategoria> getListaSubCategoria() {
		return listaSubCategoria;
	}

	public void setListaSubCategoria(List<SubCategoria> listaSubCategoria) {
		this.listaSubCategoria = listaSubCategoria;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Servico> getListaFiltrada() {
		return listaFiltrada;
	}

	public void setListaFiltrada(List<Servico> listaFiltrada) {
		this.listaFiltrada = listaFiltrada;
	}

	public StreamedContent getImgServico() {
		return imgServico;
	}

	public void setImgServico(StreamedContent imgServico) {
		this.imgServico = imgServico;
	}
	
	
	
}
