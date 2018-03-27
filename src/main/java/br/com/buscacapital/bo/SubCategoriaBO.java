package br.com.buscacapital.bo;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.buscacapital.dao.CategoriaDAO;
import br.com.buscacapital.dao.SubCategoriaDAO;
import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.Categoria;
import br.com.buscacapital.model.SubCategoria;

@Service("subCategoriaBO")
public class SubCategoriaBO {

	private static Logger log = Logger.getLogger(SubCategoriaBO.class);
	
	SubCategoriaDAO subCategoriaDAO;
	
	@Autowired
	public void setSubCategoriaDao(@Qualifier("subCategoriaDAO") SubCategoriaDAO subCategoriaDAO) {
		this.subCategoriaDAO = subCategoriaDAO;
		this.subCategoriaDAO.setPersistentClass(SubCategoria.class);
	}
	
	/**
	 * 
	 * @param categoria
	 */
	public void salvarSubCategoria(SubCategoria subCategoria) {
		try {
			this.subCategoriaDAO.salvarSubCategoria(subCategoria);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e);
		}
	}
	
	/**
	 * 
	 * @param categoria
	 */
	public void excluirSubCategoria (SubCategoria subCategoria) {
		try {
			this.subCategoriaDAO.excluirSubCategoria(subCategoria);
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
	public SubCategoria buscarPorCodigo(Long codigo) {
		try {
			return this.subCategoriaDAO.buscarPorCodigo(codigo);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<SubCategoria> listarTodos() {
		try {
			return this.subCategoriaDAO.listarTodos();
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e);
		}
	}
	
	/**
	 * 
	 * @param categoria
	 * @return
	 */
	public List<SubCategoria> listarPorCategoria(Categoria categoria){
		try {
			return this.subCategoriaDAO.listarPorCategoria(categoria);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e);
		}
	}
	
	
}
