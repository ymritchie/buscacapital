package br.com.buscacapital.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.buscacapital.dao.UsuarioDAO;
import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.Usuario;
import br.com.buscacapital.util.BuscaCapitalUtils;

@Service("usuarioBO")
public class UsuarioBO {

	UsuarioDAO usuarioDAO;
	
	@Autowired
	public void setUsuarioDao (@Qualifier("usuarioDAO") UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
		this.usuarioDAO.setPersistentClass(Usuario.class);
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @since 14/03/2018
	 * @param usuario
	 */
	public void salvarUsuario(Usuario usuario) {
		try {
			if (usuario.getCodigo() == null) {
				String senhaCriptografada = BuscaCapitalUtils.convertStringToMd5(usuario.getSenha());
				usuario.setSenha(senhaCriptografada);
			}
			
			if (usuario.getTelefone() != null || !usuario.getTelefone().isEmpty()) {
				usuario.setTelefone(usuario.getTelefone().replaceAll("[^0-9]", ""));
			}
			
			if (usuario.getAdministrador() == null) {
				usuario.setAdministrador(false);
			}
				
			this.usuarioDAO.salvarUsuario(usuario);
		} catch (Exception e) {
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	/**
	 * @author Yanisley Mora Ritchie
	 * @param usuario
	 */
	public void excluirUsuario (Usuario usuario) {
		try {
			this.usuarioDAO.excluirUsuario(usuario);
		} catch (Exception e) {
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	/**
	 * @author Yanisley Mora Ritchie
	 * @param codigo
	 * @return {@link Usuario}
	 */
	public Usuario buscarPorCodigo(Long codigo) {
		try {
			return this.usuarioDAO.buscarPorCodigo(codigo);
		} catch (Exception e) {
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @param login
	 * @param senha
	 * @return
	 */
	public Usuario buscarPorLoginSenha (String login, String senha) {
		try {
			login = login.toLowerCase().trim();
			senha = BuscaCapitalUtils.convertStringToMd5(senha);
			return this.usuarioDAO.buscarPorLoginSenha(login, senha);
		} catch (Exception e) {
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	/**
	 * Yanisley Mora Ritchie
	 * @param login
	 * @return
	 */
	public boolean buscarPorLogin (String login) {
		return this.usuarioDAO.buscarPorLogin(login.toLowerCase().trim());
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @param email
	 * @return
	 */
	public boolean buscarPorEmail (String email) {
		return this.usuarioDAO.buscarPorEmail(email.toLowerCase().trim());
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @return
	 */
	public String gerarNovaSenha() {
        String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                      "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                      "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
                      "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                      "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                      "W", "X", "Y", "Z" };

        String senha = "";

        for (int x = 0; x < 10; x++) {
               int j = (int) (Math.random() * carct.length);
               senha += carct[j];

        }

        return senha;
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @param email
	 */
	public void gerarNovaSenha(String email) {
		String novaSenha = this.gerarNovaSenha();
	}
}
