package br.com.buscacapital.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.buscacapital.bo.CategoriaBO;
import br.com.buscacapital.bo.SubCategoriaBO;
import br.com.buscacapital.contex.SessionContext;
import br.com.buscacapital.model.Categoria;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.model.SubCategoria;
import br.com.buscacapital.model.Usuario;
import br.com.buscacapital.util.Constantes;
import br.com.buscacapital.util.Mensagens;

@Scope("session")
@Component("subCategoriaController")
public class SubCategoriaController {
	
	private static final String FW_MANTER_SUB_CATEGORIAS = "/admin/manter-sub-categorias.xhtml" + Constantes.PARAMETRO_JSF_REDIRECT;
	private static Logger log = Logger.getLogger(SubCategoriaController.class);
	
	private boolean pesquisaSubCategoria;
	
	@Autowired
	private SubCategoriaBO subCategoriaBO;
	
	@Autowired
	private CategoriaBO categoriaBO;
	
	private SubCategoria subCategoria;
	
	private List<Categoria> listaCategoria;
	
	private List<SubCategoria> listaSubCategoria;
	
	private List<SubCategoria> listaFiltrada;
	
	/**
	 * Inicia a tela de manter categórias
	 * @return
	 */
	public String iniciarSubCategoria() {
		this.pesquisaSubCategoria = true;
		this.listaCategoria = new ArrayList<Categoria>(this.categoriaBO.listarTodos());
		this.listaSubCategoria = new ArrayList<SubCategoria>(this.subCategoriaBO.listarTodos());
		
		return FW_MANTER_SUB_CATEGORIAS;
	}
	
	/**
	 * Inclue e prepara uma nova categória para inserção
	 */
	public void incluirSubCategoria() {
		this.subCategoria = new SubCategoria();
		
		Cliente cliente = (Cliente) SessionContext.getInstance().getClienteSessao();
		
		List<Categoria> listaTemp = this.listaCategoria;
		
		if (cliente != null) {
			this.listaCategoria = listaTemp.stream().filter(c -> c.getCliente() != null && c.getCliente().getCodigo() == cliente.getCodigo()).collect(Collectors.toList());
		}
		
		this.pesquisaSubCategoria = false;
	}
	
	public void editarSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
		this.pesquisaSubCategoria = false;
	}
	
	public void excluirSubCategoria(SubCategoria subCategoria) {
		try {
			this.subCategoriaBO.excluirSubCategoria(subCategoria);
			this.listaSubCategoria = new ArrayList<SubCategoria>(this.subCategoriaBO.listarTodos());
			Mensagens.addMsgInfo("Subcategoria excluída com sucesso!");
		} catch (Exception e) {
			log.error(e);
			Mensagens.addMsgErro(e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void cancelarCadastroSubCategoria() {
		this.pesquisaSubCategoria = true;
		this.listaSubCategoria = new ArrayList<SubCategoria>(this.subCategoriaBO.listarTodos());
	}
	
	public void salvarSubCategoria() {
		try {
			this.subCategoriaBO.salvarSubCategoria(this.subCategoria);
			Mensagens.addMsgInfo("Subcategoria salva com sucesso!");
			cancelarCadastroSubCategoria();
		} catch (Exception e) {
			log.error(e);
			Mensagens.addMsgErro(e.getMessage());
		}
	}
	
	
	public boolean usuarioPodeEditar(SubCategoria sub) {
		Cliente cliente = (Cliente) SessionContext.getInstance().getClienteSessao();
		
		Usuario usuario = (Usuario) SessionContext.getInstance().getUsuarioLogado();
		
		if (usuario.getAdministrador()) {
			return true;
		} else if (cliente == null || cliente.getCodigo() == null) {
			return false;
		} else if (sub.getCategoria().getCliente() != null) {
			if (sub.getCategoria().getCliente().getCodigo() == cliente.getCodigo()) {
				return true;
			}
		} 
		
		return false;
	}

	public boolean isPesquisaSubCategoria() {
		return pesquisaSubCategoria;
	}

	public void setPesquisaSubCategoria(boolean pesquisaSubCategoria) {
		this.pesquisaSubCategoria = pesquisaSubCategoria;
	}

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public List<SubCategoria> getListaFiltrada() {
		return listaFiltrada;
	}

	public void setListaFiltrada(List<SubCategoria> listaFiltrada) {
		this.listaFiltrada = listaFiltrada;
	}

	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public List<SubCategoria> getListaSubCategoria() {
		return listaSubCategoria;
	}

	public void setListaSubCategoria(List<SubCategoria> listaSubCategoria) {
		this.listaSubCategoria = listaSubCategoria;
	}
	
}
