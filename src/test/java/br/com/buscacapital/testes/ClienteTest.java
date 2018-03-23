package br.com.buscacapital.testes;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.buscacapital.bo.ClienteBO;
import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.ClientePf;
import br.com.buscacapital.testes.base.TesteBase;

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
	public void testSalvarCliente() {
		try {
			ClientePf clientepf = new ClientePf();
			clientepf.setEmail("mora@gmail.com");
			clientepf.setTelefone("61986630794");
			clientepf.setEnderecoUf("DF");
			clientepf.setEnderecoBairro("Área Octogonal");
			clientepf.setEnderecoCep("70660081");
			clientepf.setEnderecoCidade("Brasília");
			clientepf.setEnderecoComplemento("Apt. 106");
			clientepf.setEnderecoLogradouro("AOS 8 Bloco A");
			clientepf.setEnderecoNumero(8);
			clientepf.setTipoCliente("PF");
			
			clientepf.setNome("Yanisley");
			clientepf.setSobrenome("Mora Ritchie");
			clientepf.setDataNascimento(new Date());
			clientepf.setCpf("70100924190");
			
			this.clienteBO.salvarCliente(clientepf);
			
			System.out.println(clientepf.toString());
			
		} catch (Exception e) {
			throw new BuscaCapitalException(e);
		}
	}

}
