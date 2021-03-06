package br.com.buscacapital.testes;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.buscacapital.bo.CategoriaBO;
import br.com.buscacapital.bo.ClienteBO;
import br.com.buscacapital.model.Categoria;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.testes.base.TesteBase;

public class CategoriaTest extends TesteBase  {

	@Autowired
	CategoriaBO categoriaBO;
	
	@Autowired
	ClienteBO clienteBO;
	
	@Test
	public void testeCadastrarCategoria() {
		Categoria categoria = new Categoria();
		
		categoria.setNome("Categoria Nome");
		categoria.setDescricao("Categoria Descrição");
		
		this.categoriaBO.salvarCategoria(categoria);
	}
	
	@Test
	public void testeCadastrarCategoriaCliente() {
		Categoria categoria = new Categoria();
		
		categoria.setNome("Categoria Nome Cliente");
		categoria.setDescricao("Categoria Descrição Cliente");
		
		Cliente cliente = this.clienteBO.buscarPorCodigo(1L);
		
		categoria.setCliente(cliente);
		
		this.categoriaBO.salvarCategoria(categoria);
	}
	
	@Test
	public void testeListarCategorias() {
		List<Categoria> listaCategoria = this.categoriaBO.listarTodos();
		
		System.out.println(listaCategoria.toString());
	}
}
