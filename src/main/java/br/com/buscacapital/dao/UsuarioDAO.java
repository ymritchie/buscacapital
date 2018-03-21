package br.com.buscacapital.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.Usuario;
/**
 * Classe que estabelece a comunicação com a entidade de persistência {@link Usuario}
 * 
 * @author  Yanisley Mora Ritchie
 * @since 21/03/2018
 *
 */
@Repository
@Qualifier("usuarioDAO")
@Scope(proxyMode = ScopedProxyMode.NO, value = "prototype")
public class UsuarioDAO extends JpaDao<Usuario> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UsuarioDAO.class);
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @since 14/03/2018
	 * @param usuario
	 * @throws BuscaCapitalException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void salvarUsuario(Usuario usuario) throws BuscaCapitalException {
		if (usuario == null) {
			throw new BuscaCapitalException("O usuário deve ser preechido antes de salvar!");
		} else {
			try {
				if (usuario.getCodigo() != null) {
					update(usuario);
				} else {
					save(usuario);
				}
			} catch (Exception e) {
				log.error(e);
				throw new BuscaCapitalException(e.getMessage());
			}
		}
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @since 14/03/2018
	 * @param usuario
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void excluirUsuario (Usuario usuario) {
		if (usuario == null) {
			throw new BuscaCapitalException("Favor informar o usuário a ser excluído!");
		} else {
			try {
				delete(usuario);
				getEntityManager().flush();
			} catch (Exception e) {
				log.error(e);
				throw new BuscaCapitalException(e.getMessage());
			}
		}
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @since 14/03/2018
	 * @param codigo
	 * @return
	 */
	public Usuario buscarPorCodigo(Long codigo) {
		try {
			getEntityManager().clear();
			return (Usuario) getEntityManager().find(Usuario.class, codigo);
		} catch (NoResultException nRe) {
			log.error(nRe);
			throw new BuscaCapitalException("Usuário não cadastrado!");
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível obter o usuário", e.getMessage());
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
			
			Usuario usuarioRetorno = null;
			
			Query query = getEntityManager().createNamedQuery("Usuario.findByLoginSenha");
			query.setParameter("login", login);
			query.setParameter("senha", senha);
			
			usuarioRetorno = (Usuario) query.getSingleResult();
			
			return usuarioRetorno;
			
		} catch (NoResultException nRe) {
			log.error(nRe);
			throw new BuscaCapitalException("Usuário ou senha Incorretos!");
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível obter o usuário", e.getMessage());
		}
	}
	
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @param login
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean buscarPorLogin (String login) {
		try {
			
			List<Usuario> listaRetorno = new ArrayList<Usuario>();
			 
			Query query = getEntityManager().createNamedQuery("Usuario.findByLogin");
			query.setParameter("login", login);
			
			listaRetorno = query.getResultList();
			
			if (listaRetorno.size() == 0) {
				return false;
			} else {
				return true;
			}
			
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @param email
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean buscarPorEmail (String email) {
		try {
			
			List<Usuario> listaRetorno = new ArrayList<Usuario>();
			 
			Query query = getEntityManager().createNamedQuery("Usuario.findByEmail");
			query.setParameter("email", email);
			
			listaRetorno = query.getResultList();
			
			if (listaRetorno.size() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
	
}
