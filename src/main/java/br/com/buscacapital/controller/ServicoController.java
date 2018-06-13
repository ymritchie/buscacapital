package br.com.buscacapital.controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.aspectj.weaver.BCException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.buscacapital.bo.ArquivoBO;
import br.com.buscacapital.bo.CategoriaBO;
import br.com.buscacapital.bo.ClienteBO;
import br.com.buscacapital.bo.ServicoBO;
import br.com.buscacapital.bo.SubCategoriaBO;
import br.com.buscacapital.contex.SessionContext;
import br.com.buscacapital.model.Arquivo;
import br.com.buscacapital.model.Categoria;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.model.Servico;
import br.com.buscacapital.model.SubCategoria;
import br.com.buscacapital.util.BCUtils;
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
	
	@Autowired
	private ArquivoBO arquivoBO;
	
	@Autowired
	private ClienteBO clienteBO;
	
	private Servico servico;
	
	private List<Servico> listaServico;
	
	private List<Servico> listaFiltrada;
	
	private List<Categoria> listaCategoria;
	
	private List<SubCategoria> listaSubCategoria;
	
	private List<Cliente> listaCliente;
	
	private Cliente cliente;
	
	private StreamedContent imgServico;
	
	private Arquivo arquivo;
	
	private byte[] bytes;
	
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
		
		this.listaCliente = new ArrayList<Cliente>(this.clienteBO.listarTodos());
		
		return FW_MANTER_SERVICO;
	}
	
	/**
	 * 
	 */
	public void incluirNovoServico() {
		this.servico = new Servico();
		if (this.cliente != null) {
			this.servico.setCliente(this.cliente);
		}
		
		this.imgServico = null;
		carregarImagemPadrao();
		this.pesquisaServico = false;
		this.arquivo = new Arquivo();
	}
	
	/**
	 * 
	 */
	public void salvarServico() {
		try {
			if (this.imgServico == null) {
				throw new BCException("Favor selecionar uma imagem antes de salvar o serviço!");
			}
		
			this.servico.setArquivo(this.arquivo);
			
			this.servicoBO.salvarServico(this.servico);
			
			Mensagens.addMsgInfo("Serviço salvo com sucesso!");
			
			this.listaServico = new ArrayList<Servico>(this.servicoBO.listarPorCliente(this.cliente));
			this.pesquisaServico = true;
			
		} catch (Exception e) {
			log.error(e);
			Mensagens.addMsgErro(e.getMessage());
		}
		

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
		this.servico = servico;
		this.arquivo = new Arquivo();
		this.arquivo = this.servico.getArquivo();
		
		InputStream is = new ByteArrayInputStream(this.servico.getArquivo().getConteudo());
		
		String nomeArquivo = this.arquivo.getNome() + "." + this.arquivo.getExtensao();
		
		String contentType ="";
		
		switch (this.arquivo.getExtensao()) {
		case "jpg":
			contentType = "image/jpeg";
			break;
		case "jpeg":
			contentType = "image/jpeg";
			break;
		case "bmp":
			contentType = "image/bmp";
			break;

		default:
			contentType ="image/png";
			break;
		}
		
		this.imgServico = new DefaultStreamedContent(is, contentType, nomeArquivo);
		
		this.pesquisaServico = false;
		
	}
	
	/**
	 * 
	 * @param event
	 */
	public String processarUploadImagem (FileUploadEvent event){
		try {
			UploadedFile arquivoUpoad = event.getFile();
			
			//Carregar o arquivo que será salvo no banco de dados
			this.arquivo.setConteudo(BCUtils.toByteArray(arquivoUpoad.getInputstream()));
			this.arquivo.setNome(arquivoUpoad.getFileName().substring(0, arquivoUpoad.getFileName().indexOf(".")));
			this.arquivo.setExtensao(arquivoUpoad.getFileName().substring(arquivoUpoad.getFileName().indexOf(".") + 1));
			this.arquivo.setTamanho(new Long((arquivoUpoad.getInputstream().available())));
			
			this.imgServico = new DefaultStreamedContent(arquivoUpoad.getInputstream(), arquivoUpoad.getContentType(), arquivoUpoad.getFileName());
		} catch (Exception e)  {
			log.error(e);
			Mensagens.addMsgErro("Não foi possível anexar o arquivo " + event.getFile().getFileName());
			return null;
		}
		
		return FW_MANTER_SERVICO;
	}
	
	private void carregarImagemPadrao() {
		try {
			String caminho  = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/template/app-assets/images/ico/" + "/");
			FileInputStream fis = new FileInputStream(caminho + "/android-chrome-512x512.png");
			InputStream is = fis;
			this.imgServico = new DefaultStreamedContent(is, "image/png");
			
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
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

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
	
	
	
}
