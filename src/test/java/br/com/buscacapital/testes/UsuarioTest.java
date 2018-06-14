package br.com.buscacapital.testes;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.buscacapital.bo.UsuarioBO;
import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.Usuario;
import br.com.buscacapital.testes.base.TesteBase;

public class UsuarioTest extends TesteBase {

	@Autowired
	UsuarioBO usuarioBO;
	
	@Test
	public void testeCadastarUsuario() {
		try {
			Usuario usuario = new Usuario();
			
			usuario.setLogin("teste");
			usuario.setSenha("maisteste");
			usuario.setNome("Teste");
			usuario.setSobrenome("Mais Teste");
			usuario.setSexo("M");
			usuario.setEmail("teste@buscacapital.com.br");
			usuario.setTelefone("30639251");
			usuario.setDataNascimento(new Date());
			
			this.usuarioBO.salvarUsuario(usuario, false, false);
			
		} catch (Exception e){
			throw new BuscaCapitalException(e);
		}
	}
}
