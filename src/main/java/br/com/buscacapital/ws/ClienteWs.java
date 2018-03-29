package br.com.buscacapital.ws;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.buscacapital.bo.ClienteBO;
import br.com.buscacapital.dto.ClienteDTO;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.model.ClientePf;
import br.com.buscacapital.model.ClientePj;

@Controller
public class ClienteWs {

	private static final Logger log = Logger.getLogger(ClienteWs.class);
	
	@Autowired
	private ClienteBO clienteBO;
	
	@RequestMapping(value="/cliente", method=RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<String> retornaClienteJson() {
		Gson gsonCliente = new GsonBuilder().create();
		log.info("Obtendo lista de Clientes");
		
		List<Cliente> listaCliente = new ArrayList<Cliente>(this.clienteBO.listarTodos());
		
		List<ClienteDTO> listaRetorno = this.trataListaCliente(listaCliente);
		
		String objRetorno = gsonCliente.toJson(listaRetorno);
		
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		return new ResponseEntity<String>(objRetorno, responseHeaders, HttpStatus.OK);
	}

	/**
	 * 
	 * @param listaCliente
	 * @return
	 */
	private List<ClienteDTO> trataListaCliente(List<Cliente> listaCliente) {
		List<ClienteDTO> listaRetorno = new ArrayList<ClienteDTO>();
		
		if (!listaCliente.isEmpty()) {
			for (Cliente cliente : listaCliente) {
				ClienteDTO cDto = new ClienteDTO();
				
				cDto.setTipo(cliente.getTipoCliente());
				cDto.setEmail(cliente.getEmail());
				
				cDto.setBairro(cliente.getEnderecoBairro());
				cDto.setCep(cliente.getEnderecoCep());
				cDto.setCidade(cliente.getEnderecoCidade());
				cDto.setComplemento(cliente.getEnderecoComplemento());
				cDto.setLogradouro(cliente.getEnderecoLogradouro());
				cDto.setUf(cliente.getEnderecoUf());
				cDto.setNumero(cliente.getEnderecoNumero());
				
				cDto.setTelefone(cliente.getTelefone());
				cDto.setCpfCnpj(cliente.getTipoCliente().equalsIgnoreCase("PF") ? ((ClientePf) cliente).getCpf() : ((ClientePj) cliente).getCnpj());
				cDto.setNome(cliente.getTipoCliente().equalsIgnoreCase("PF") ? ((ClientePf) cliente).getNomeCompleto() : ((ClientePj) cliente).getNomeFantasia());
				
				listaRetorno.add(cDto);
			}
			
			
		}
		
		return listaRetorno;
	}
}
