package br.com.buscacapital.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.buscacapital.bo.CategoriaBO;
import br.com.buscacapital.contex.SessionContext;
import br.com.buscacapital.model.Categoria;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.util.Constantes;
import br.com.buscacapital.util.Mensagens;

@Scope("session")
@Component("categoriaController")
public class CategoriaController {
	
	private static final String FW_MANTER_CATEGORIAS = "/admin/manter-categorias.xhtml" + Constantes.PARAMETRO_JSF_REDIRECT;
	private static Logger log = Logger.getLogger(CategoriaController.class);
	
	private boolean pesquisaCategoria;
	
	@Autowired
	private CategoriaBO categoriaBO;
	
	private Categoria categoria;
	
	private List<Categoria> listaCategoria;
	
	private List<Categoria> listaFiltrada;
	
	/**
	 * Inicia a tela de manter categórias
	 * @return
	 */
	public String iniciarCategoria() {
		this.pesquisaCategoria = true;
		this.listaCategoria = new ArrayList<Categoria>(this.categoriaBO.listarTodos());
		
		return FW_MANTER_CATEGORIAS;
	}
	
	/**
	 * Inclue e prepara uma nova categória para inserção
	 */
	public void incluirCategoria() {
		this.categoria = new Categoria();
		Cliente cliente = (Cliente) SessionContext.getInstance().getClienteSessao();
		
		if (cliente != null) {
			this.categoria.setCliente(cliente);
		}
		
		this.pesquisaCategoria = false;
	}
	
	public void editarCategoria(Categoria categoria) {
		this.categoria = categoria;
		this.pesquisaCategoria = false;
	}
	
	public void excluirCategoria(Categoria categoria) {
		try {
			this.categoriaBO.excluirCategoria(categoria);
			this.listaCategoria = new ArrayList<Categoria>(this.categoriaBO.listarTodos());
			Mensagens.addMsgInfo("Categoria excluída com sucesso!");
		} catch (Exception e) {
			log.error(e);
			Mensagens.addMsgErro(e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void cancelarCadastroCategoria() {
		this.pesquisaCategoria = true;
		this.listaCategoria = new ArrayList<Categoria>(this.categoriaBO.listarTodos());
	}
	
	public void salvarCategoria() {
		try {
			this.categoriaBO.salvarCategoria(this.categoria);
			Mensagens.addMsgInfo("Categoria salva com sucesso!");
			cancelarCadastroCategoria();
		} catch (Exception e) {
			log.error(e);
			Mensagens.addMsgErro(e.getMessage());
		}
	}

	public boolean isPesquisaCategoria() {
		return pesquisaCategoria;
	}

	public void setPesquisaCategoria(boolean pesquisaCategoria) {
		this.pesquisaCategoria = pesquisaCategoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public List<Categoria> getListaFiltrada() {
		return listaFiltrada;
	}

	public void setListaFiltrada(List<Categoria> listaFiltrada) {
		this.listaFiltrada = listaFiltrada;
	}
	
	

	
}
