package br.com.buscacapital.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import br.com.buscacapital.model.Servico;

/**
 * Classe que estabelece a comunicação com a entidade de persistência {@link Servico} 
 * 
 * @author  Yanisley Mora Ritchie
 * @since 21/03/2018
 *
 */
@Repository
@Qualifier("servicoDAO")
@Scope(proxyMode = ScopedProxyMode.NO, value = "prototype")
public class ServicoDAO extends JpaDao<Servico> implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ServicoDAO.class);
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void salvarServico(Servico servico) throws BuscaCapitalException {
		if (servico == null) {
			throw new BuscaCapitalException("O Serviço deve ser preechido antes de salvar!");
		} else {
			try {
				if (servico.getCodigo() != null) {
					update(servico);
				} else {
					save(servico);
				}
			} catch (Exception e) {
				log.error(e);
				throw new BuscaCapitalException(e.getMessage());
			}
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void excluirServico (Servico servico) {
		if (servico == null) {
			throw new BuscaCapitalException("Favor informar o serviço a ser excluído!");
		} else {
			try {
				delete(servico);
				getEntityManager().flush();
			} catch (Exception e) {
				log.error(e);
				throw new BuscaCapitalException(e.getMessage());
			}
		}
	}
	
	public Servico buscarPorCodigo(Long codigo) {
		try {
			getEntityManager().clear();
			return (Servico) getEntityManager().find(Servico.class, codigo);
		} catch (NoResultException nRe) {
			log.error(nRe);
			throw new BuscaCapitalException("Serviço não cadastrado!");
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível obter o serviço", e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Servico> listarTodos() {
		try {
			List<Servico>  listaRetorno = new ArrayList<Servico>();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT SER FROM Servico SER ");
			
			listaRetorno = getEntityManager().createQuery(sql.toString()).getResultList();
			
			return listaRetorno;
					
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível obter a lista de Serviços");
		}
	}

	/**
	 * Retorna uma lista de Servicos associados ao ciente logado no sistema 
	 * @param cliente
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Servico> listarPorCliente(Cliente cliente) {
		try {
			List<Servico>  listaRetorno = new ArrayList<Servico>();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT SER FROM Servico SER WHERE SER.cliente = ? ");
			
			listaRetorno = getEntityManager().createQuery(sql.toString()).setParameter(1, cliente).getResultList();
			
			return listaRetorno;
					
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível obter a lista de Serviços");
		}
	}
	
	
}
