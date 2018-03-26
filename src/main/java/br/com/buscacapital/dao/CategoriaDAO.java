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

@Repository
@Qualifier("categoriaDAO")
@Scope(proxyMode = ScopedProxyMode.NO, value = "prototype")
public class CategoriaDAO extends JpaDao<Categoria> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(CategoriaDAO.class);
	
	/**
	 * 
	 * @param categoria
	 * @throws BuscaCapitalException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void salvarCategoria(Categoria categoria) throws BuscaCapitalException {
		if (categoria == null) {
			throw new BuscaCapitalException("A categoria deve sr inicializada antes de salvar!");
		} else {
			try {
				if (categoria.getCodigo() != null) {
					update(categoria);
				} else {
					save(categoria);
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
	public void excluirCategoria (Categoria categoria) {
		if (categoria == null) {
			throw new BuscaCapitalException("A categoria deve ser inicializada antes de excluir!");
		} else {
			try {
				delete(categoria.getCodigo());
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
	public Categoria buscarPorCodigo(Long codigo) {
		try {
			getEntityManager().clear();
			return (Categoria) getEntityManager().find(Categoria.class, codigo);
			
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
	public List<Categoria> listarTodos() {
		try {
			List<Categoria> listaRetorno = new ArrayList<Categoria>();
			listaRetorno = getEntityManager().createNamedQuery("Categoria.findAll").getResultList();
			
			return listaRetorno;
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível realizar esta operação", e);
		}
	}
}
