package br.com.buscacapital.dao;

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
import br.com.buscacapital.model.Categoria;
import br.com.buscacapital.model.SubCategoria;

@Repository
@Qualifier("subCategoriaDAO")
@Scope(proxyMode = ScopedProxyMode.NO, value = "prototype")
public class SubCategoriaDAO extends JpaDao<SubCategoria> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(SubCategoriaDAO.class);
	
	/**
	 * 
	 * @param subCategoria
	 * @throws BuscaCapitalException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void salvarSubCategoria(SubCategoria subCategoria) throws BuscaCapitalException {
		if (subCategoria == null) {
			throw new BuscaCapitalException("A Subcategoria deve sr inicializada antes de salvar!");
		} else {
			try {
				if (subCategoria.getCodigo() != null) {
					update(subCategoria);
				} else {
					save(subCategoria);
				}
			} catch (Exception e) {
				log.error(e);
				throw new BuscaCapitalException(e);
			}
		}
	}
	
	/**
	 * 
	 * @param categoria
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void excluirSubCategoria (SubCategoria subCategoria) {
		if (subCategoria == null) {
			throw new BuscaCapitalException("A categoria deve ser inicializada antes de excluir!");
		} else {
			try {
				delete(subCategoria.getCodigo());
			} catch (Exception e) {
				log.error(e);
				throw new BuscaCapitalException(e);
			}
		}
	}
	
	/**
	 * 
	 * @param codigo
	 * @return
	 */
	public SubCategoria buscarPorCodigo(Long codigo) {
		try {
			getEntityManager().clear();
			return (SubCategoria) getEntityManager().find(SubCategoria.class, codigo);
			
		} catch (NoResultException nRe) {
			log.error(nRe);
			throw new BuscaCapitalException("Não foi encontrado o código solicitado.");
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível realizar esta operação", e);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SubCategoria> listarTodos() {
		try {
			List<SubCategoria> listaRetorno = new ArrayList<SubCategoria>();
			listaRetorno = getEntityManager().createNamedQuery("SubCategoria.findAll").getResultList();
			
			return listaRetorno;
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível realizar esta operação", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SubCategoria> listarPorCategoria(Categoria categoria){
		try {
			List<SubCategoria> listaRetorno = new ArrayList<SubCategoria>();
			String sql = "SELECT SC FROM SubCategoria SC WHERE SC.categoria = ?";
			listaRetorno = getEntityManager().createQuery(sql).setParameter(1, categoria).getResultList();
			
			return listaRetorno;
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível realizar esta operação", e);
		}
	}
}
