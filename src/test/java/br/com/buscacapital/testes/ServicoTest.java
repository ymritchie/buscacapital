package br.com.buscacapital.testes;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.buscacapital.bo.ServicoBO;
import br.com.buscacapital.testes.base.TesteBase;

public class ServicoTest extends TesteBase {

	@Autowired
	ServicoBO servicoBO;
	
	@Test
	public void cadastrarServico() {
		
	}
}
