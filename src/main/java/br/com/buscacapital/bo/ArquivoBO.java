package br.com.buscacapital.bo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.buscacapital.dao.ArquivoDAO;
import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.Arquivo;

/**
 * Estabelece a comunicação entre o controller e o DAO além de tratar as regras de negócio
 * 
 * @author  Yanisley Mora Ritchie
 * @since 04/04/2018
 *
 */

@Service("arquivoBO")
public class ArquivoBO {
	private static Logger log = Logger.getLogger(ArquivoBO.class);
	
	ArquivoDAO arquivoDAO;
	
	@Autowired
	public void setArquivoDao (@Qualifier("arquivoDAO") ArquivoDAO arquivoDAO) {
		this.arquivoDAO = arquivoDAO;
		this.arquivoDAO.setPersistentClass(Arquivo.class);
	}
	
	
	public void salvarArquivo(Arquivo arquivo) {
		try {
			this.arquivoDAO.salvarArquivo(arquivo);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	public void excluirArquivo(Arquivo arquivo) {
		try {
			this.arquivoDAO.excluirArquivo(arquivo);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	public Arquivo buscarPorCodigo(Long codigo) {
		try {
			return this.arquivoDAO.buscarPorCodigo(codigo);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	
}
