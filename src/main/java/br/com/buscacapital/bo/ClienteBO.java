package br.com.buscacapital.bo;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.buscacapital.dao.ClienteDAO;
import br.com.buscacapital.exception.BuscaCapitalException;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.model.Usuario;

/**
 * Estabelece a comunicação entre o controller e o DAO além de tratar as regras de negócio
 * 
 * @author  Yanisley Mora Ritchie
 * @since 21/03/2018
 *
 */

@Service("clienteBO")
public class ClienteBO {

	private static Logger log = Logger.getLogger(ClienteBO.class);
	ClienteDAO clienteDAO;
	
	
	@Autowired
	public void setClineteDao (@Qualifier("clienteDAO") ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
		this.clienteDAO.setPersistentClass(Cliente.class);
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @param cliente
	 */
	public void salvarCliente(Cliente cliente) {
		try {
			if (!cliente.getEnderecoCep().isEmpty() || cliente.getEnderecoCep() != null) {
				cliente.setEnderecoCep(cliente.getEnderecoCep().replaceAll("[^0-9]", ""));
			}
			
			this.clienteDAO.salvarCliente(cliente);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @param cliente
	 */
	public void excluirCliente (Cliente cliente) {
		try {
			this.clienteDAO.excluirCliente(cliente);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @param codigo
	 * @return cliente
	 */
	public Cliente buscarPorCodigo(Long codigo) {
		try {
			return this.clienteDAO.buscarPorCodigo(codigo);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e.getMessage());
		}
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @param usuario
	 * @return cliente
	 */
	public Cliente buscarPorUsuario(Usuario usuario) {
		try {
			return this.clienteDAO.buscarPorUsuario(usuario);
		} catch (Exception e) {
			log.error(e);
			throw new BuscaCapitalException(e.getMessage());
		}
	}

	/**
	 * Pesquisa todos os clientes banco de dados
	 * 
	 * @return
	 */
	public List<Cliente> listarTodos() {
		try {
			return this.clienteDAO.listarTodos();
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	
}
