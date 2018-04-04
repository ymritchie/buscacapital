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
import br.com.buscacapital.model.Arquivo;


@Repository
@Qualifier("arquivoDAO")
@Scope(proxyMode = ScopedProxyMode.NO, value = "prototype")
public class ArquivoDAO extends JpaDao<Arquivo> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ArquivoDAO.class);
	
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @since 04/04/2018
	 * @param arquivo
	 * @throws BuscaCapitalException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void salvarArquivo(Arquivo arquivo) throws BuscaCapitalException {
		if (arquivo == null) {
			throw new BuscaCapitalException("O arquivo deve ser preechido antes de salvar!");
		} else {
			try {
				if (arquivo.getCodigo() != null) {
					update(arquivo);
				} else {
					save(arquivo);
				}
			} catch (Exception e) {
				log.error(e);
				throw new BuscaCapitalException(e.getMessage());
			}
		}
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @since 04/04/2018
	 * @param usuario
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void excluirArquivo (Arquivo arquivo) {
		if (arquivo == null) {
			throw new BuscaCapitalException("Favor informar o arquivo a ser excluído!");
		} else {
			try {
				delete(arquivo);
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
	public Arquivo buscarPorCodigo(Long codigo) {
		try {
			getEntityManager().clear();
			return (Arquivo) getEntityManager().find(Arquivo.class, codigo);
		} catch (NoResultException nRe) {
			log.error(nRe);
			throw new BuscaCapitalException("Arquivo não cadastrado!");
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException("Não foi possível obter o arquivo", e.getMessage());
		}
	}
}
