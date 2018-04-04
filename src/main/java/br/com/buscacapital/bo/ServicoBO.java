package br.com.buscacapital.bo;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.buscacapital.dao.ServicoDAO;
import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.model.Servico;

/**
 * Estabelece a comunicação entre o controller e o DAO além de tratar as regras de negócio
 * 
 * @author  Yanisley Mora Ritchie
 * @since 04/04/2018
 *
 */
@Service("servicoBO")
public class ServicoBO {

	private static Logger log = Logger.getLogger(ServicoBO.class);
	
	ServicoDAO servicoDAO;
	
	@Autowired
	private ArquivoBO arquivoBO;
	
	@Autowired
	public void setServicoDao (@Qualifier("servicoDAO") ServicoDAO servicoDAO) {
		this.servicoDAO = servicoDAO;
		this.servicoDAO.setPersistentClass(Servico.class);
	}
	
	/**
	 * Salva o serviço
	 * @param servico
	 */
	public void salvarServico(Servico servico) {
		try {
			this.servicoDAO.salvarServico(servico);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	/**
	 * Exclui o serviço
	 * @param servico
	 */
	public void excluirServico (Servico servico) {
		try {
			this.servicoDAO.excluirServico(servico);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param codigo
	 * @return {@link Servico}
	 */
	public Servico buscarPorCodigo(Long codigo) {
		try {
			return this.servicoDAO.buscarPorCodigo(codigo);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Servico> listarTodos() {
		try {
			return this.servicoDAO.listarTodos();
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	/**
	 * Listar Serviços por cliente
	 * @param cliente
	 * @return
	 */
	public List<Servico> listarPorCliente(Cliente cliente) {
		try {
			if (cliente != null) {
				return this.servicoDAO.listarPorCliente(cliente);
			} else {
				return this.servicoDAO.listarTodos();
			}
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}
