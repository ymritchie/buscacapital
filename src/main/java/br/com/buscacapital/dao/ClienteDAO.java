package br.com.buscacapital.dao;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.model.Usuario;

/**
 * Classe que estabelece a comunicação com a entidade de persistência {@link Cliente} 
 * 
 * @author  Yanisley Mora Ritchie
 * @since 21/03/2018
 *
 */

@Repository
@Qualifier("clienteDAO")
@Scope(proxyMode = ScopedProxyMode.NO, value = "prototype")
public class ClienteDAO extends JpaDao<Cliente> implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ClienteDAO.class);
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @since 21/03/2018
	 * @param cliente
	 * @throws BuscaCapitalException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void salvarCliente(Cliente cliente) throws BuscaCapitalException {
		if (cliente == null) {
			throw new BuscaCapitalException("O cliente deve ser preechido antes de salvar!");
		} else {
			try {
				if (cliente.getCodigo() != null) {
					update(cliente);
				} else {
					save(cliente);
				}
			} catch (Exception e) {
				log.error(e);
				throw new BuscaCapitalException(e.getMessage());
			}
		}
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @since 21/03/2018
	 * @param usuario
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void excluirCliente (Cliente cliente) {
		if (cliente == null) {
			throw new BuscaCapitalException("Favor informar o cliente a ser excluído!");
		} else {
			try {
				delete(cliente);
				getEntityManager().flush();
			} catch (Exception e) {
				log.error(e);
				throw new BuscaCapitalException(e.getMessage());
			}
		}
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @since 21/03/2018
	 * @param codigo
	 * @return
	 */
	public Cliente buscarPorCodigo(Long codigo) {
		try {
			getEntityManager().clear();
			return (Cliente) getEntityManager().find(Cliente.class, codigo);
		} catch (NoResultException nRe) {
			log.error(nRe);
			throw new BuscaCapitalException("Cliente não cadastrado!");
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível obter o cliente", e.getMessage());
		}
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @since 21/03/2018
	 * @param codigo
	 * @return
	 */
	public Cliente buscarPorUsuario(Usuario usuario) {
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT CLI FROM Cliente CLI WHERE CLI.usuario = ? ");
			return (Cliente) getEntityManager().createQuery(sql.toString()).setParameter(1, usuario).getSingleResult();
			
		} catch (NoResultException nRe) {
			log.error(nRe);
			throw new BuscaCapitalException("Cliente não cadastrado!");
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível obter o cliente", e.getMessage());
		}
	}
	
}
