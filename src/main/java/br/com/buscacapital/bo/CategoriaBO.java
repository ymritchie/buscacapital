package br.com.buscacapital.bo;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.buscacapital.dao.CategoriaDAO;
import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.Categoria;

@Service("categoriaBO")
public class CategoriaBO {

	private static Logger log = Logger.getLogger(CategoriaBO.class);
	
	CategoriaDAO categoriaDAO;
	
	@Autowired
	public void setCategoriaDao(@Qualifier("categoriaDAO") CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
		this.categoriaDAO.setPersistentClass(Categoria.class);
	}
	
	/**
	 * 
	 * @param categoria
	 */
	public void salvarCategoria(Categoria categoria) {
		try {
			this.categoriaDAO.salvarCategoria(categoria);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e);
		}
	}
	
	/**
	 * 
	 * @param categoria
	 */
	public void deletarCategoria (Categoria categoria) {
		try {
			this.categoriaDAO.deletarCategoria(categoria);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e);
		}
	}
	
	/**
	 * 
	 * @param codigo
	 * @return {@link categoria}
	 */
	public Categoria buscarPorCodigo(Long codigo) {
		try {
			return this.categoriaDAO.buscarPorCodigo(codigo);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Categoria> listarTodos() {
		try {
			return this.categoriaDAO.listarTodos();
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e);
		}
	}
	
	
}
