package br.com.buscacapital.testes;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.buscacapital.bo.ArquivoBO;
import br.com.buscacapital.bo.CategoriaBO;
import br.com.buscacapital.bo.ClienteBO;
import br.com.buscacapital.bo.ServicoBO;
import br.com.buscacapital.model.Arquivo;
import br.com.buscacapital.model.Categoria;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.model.Servico;
import br.com.buscacapital.testes.base.TesteBase;

public class ServicoTest extends TesteBase {

	@Autowired
	ServicoBO servicoBO;
	
	@Autowired
	CategoriaBO categoriaBO;
	
	@Autowired
	ClienteBO clienteBO;
	
	@Autowired
	ArquivoBO arquivoBO;
	
	@Test
	public void testeCadastrarServico() {
		Servico servico = new Servico();
		
		Categoria categoria = categoriaBO.buscarPorCodigo(3L);
		
		Cliente cliente = clienteBO.buscarPorCodigo(4L);
		
		Arquivo arquivo = arquivoBO.buscarPorCodigo(2L);
		
		servico.setCategoria(categoria);
		servico.setCliente(cliente);
		servico.setArquivo(arquivo);
		servico.setNome("Frete");
		servico.setDescricao("Transporte de volumes pequenos");
		servico.setPreco(34.67);
		
		this.servicoBO.salvarServico(servico);
	}
	
	@Test
	public void testeListarServicos() {
		List<Servico> listaServico = this.servicoBO.listarTodos();
		
		System.out.println(listaServico);
	}
}
