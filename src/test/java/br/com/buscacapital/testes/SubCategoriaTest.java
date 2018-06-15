package br.com.buscacapital.testes;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.buscacapital.bo.CategoriaBO;
import br.com.buscacapital.bo.SubCategoriaBO;
import br.com.buscacapital.model.Categoria;
import br.com.buscacapital.model.SubCategoria;
import br.com.buscacapital.testes.base.TesteBase;

public class SubCategoriaTest extends TesteBase {

	@Autowired
	SubCategoriaBO subcategoriaBO; 
	
	@Autowired
	CategoriaBO categoriaBO;
	
	@Test
	public void testeCadastrarSubCategoria() {
		SubCategoria subCategoria = new SubCategoria();
		
		Categoria categoria = categoriaBO.buscarPorCodigo(3L);
		
		subCategoria.setCategoria(categoria);
		subCategoria.setNome("Nome Subcategoria");
		subCategoria.setDescricao("Descrição Subcategoria");
		
		this.subcategoriaBO.salvarSubCategoria(subCategoria);
	}
}
