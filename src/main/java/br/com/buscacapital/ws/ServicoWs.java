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

import br.com.buscacapital.bo.ServicoBO;
import br.com.buscacapital.dto.ServicoDTO;
import br.com.buscacapital.model.ClientePf;
import br.com.buscacapital.model.ClientePj;
import br.com.buscacapital.model.Servico;

@Controller
public class ServicoWs {

	private static final Logger log = Logger.getLogger(ServicoWs.class);
	
	@Autowired
	private ServicoBO servicoBO;
	
	@RequestMapping(value="/servicos", method=RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<String> retornarServicoJson(){
		Gson gsonServicos = new GsonBuilder().create();
		log.info("Obtendo lista de Servicos");
		
		List<Servico> listaServicos = new ArrayList<Servico>(servicoBO.listarTodos());
		
		List<ServicoDTO> listaRetorno = this.montarListaServicos(listaServicos);
		
		String objRetorno = gsonServicos.toJson(listaRetorno);
		
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		return new ResponseEntity<String>(objRetorno, responseHeaders, HttpStatus.OK);
		
		
	}

	private List<ServicoDTO> montarListaServicos(List<Servico> listaServicos) {
		List<ServicoDTO> listaServicoDTO = new ArrayList<ServicoDTO>();
		
		if (!listaServicos.isEmpty()) {
			for (Servico item : listaServicos) {
				ServicoDTO sDto = new ServicoDTO();
				sDto.setImagem(item.getArquivo().getConteudo());
				sDto.setNomeServico(item.getNome());
				sDto.setDescricaoServico(item.getDescricao());
				sDto.setPrecoServico(item.getPreco());
				
				sDto.setIdCliente(item.getCliente().getCodigo());
				sDto.setNomeCliente(item.getCliente().getTipoCliente().equalsIgnoreCase("PF") ? ((ClientePf) item.getCliente()).getNomeCompleto() : ((ClientePj) item.getCliente()).getNomeFantasia());
				
				sDto.setIdCategoria(item.getCliente().getCodigo());
				sDto.setNomeCategoria(item.getCategoria().getNome());
				
				listaServicoDTO.add(sDto);
			}
		}
		
		return listaServicoDTO;
		
	}
}
