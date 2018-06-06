package br.com.buscacapital.testes;


import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.buscacapital.bo.ClienteBO;
import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.ClientePf;
import br.com.buscacapital.model.ClientePj;
import br.com.buscacapital.testes.base.TesteBase;
import br.com.buscacapital.util.BCUtils;

/**
 * Teste unitário para cadastar novos clientes
 * 
 * @author  Yanisley Mora Ritchie
 *
 */

public class ClienteTest extends TesteBase {

	@Autowired
	ClienteBO clienteBO;
	
	@Test
	public void testSalvarClientePf() {
		try {
			ClientePf clientePf = new ClientePf();
			clientePf.setEmail("clientepf@buscacapital.com.br");
			clientePf.setTelefone("6132920569");
			clientePf.setEnderecoUf("DF");
			clientePf.setEnderecoBairro("Cruzeiro Novo");
			clientePf.setEnderecoCep("70660081");
			clientePf.setEnderecoCidade("Brasília");
			clientePf.setEnderecoComplemento("Apt. 106");
			clientePf.setEnderecoLogradouro("SHCES 302 BL D");
			clientePf.setEnderecoNumero(8);
			clientePf.setTipoCliente("PF");
			
			clientePf.setNome("Cliente");
			clientePf.setSobrenome("PF");
			clientePf.setDataNascimento(new Date());
			clientePf.setCpf("87485457918");
			
			this.clienteBO.salvarCliente(clientePf);
			
			System.out.println(clientePf.toString());
			
		} catch (Exception e) {
			throw new BuscaCapitalException(e);
		}
	}
	
	@Test
	public void testSalvarClientePj() {
		try {
			ClientePj clientePj = new ClientePj();
			
			clientePj.setEmail("clientepj@buscacapital.com.br");
			clientePj.setTelefone("6132920569");
			clientePj.setEnderecoUf("DF");
			clientePj.setEnderecoBairro("Sudoeste");
			clientePj.setEnderecoCep("70660385");
			clientePj.setEnderecoCidade("Brasília");
			clientePj.setEnderecoComplemento("Apt. 106");
			clientePj.setEnderecoLogradouro("CLSH 302 BL A");
			clientePj.setEnderecoNumero(5);
			clientePj.setTipoCliente("PJ");
			
			clientePj.setRazaoSocial("TACOS E VINHOS S.A");
			clientePj.setNomeFantasia("LA BIGORNIA");
			clientePj.setDataInscricao(new Date());
			clientePj.setCnpj("16037461000179");
			
			this.clienteBO.salvarCliente(clientePj);
		} catch (Exception e) {
			throw new BuscaCapitalException(e);
		}
		
	}
	
	@Test
	public void testValidarCpf() {
		assertTrue("CPF Valido", BCUtils.isCpfValido("94677933774"));
	}
	
	@Test
	public void testValidarCnpj() {
		assertTrue("CNPJ Valido", BCUtils.isCnpjValido("44725865000190"));
	}
	
	@Test
	public void testListarClientes() {
		try {
			assertNotNull("Lista de Clientes", this.clienteBO.listarTodos());
		} catch (Exception e) {
			throw new BuscaCapitalException(e);
		}
	}

}
