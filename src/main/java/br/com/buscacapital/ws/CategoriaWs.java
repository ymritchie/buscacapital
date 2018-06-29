package br.com.buscacapital.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import br.com.buscacapital.bo.CategoriaBO;
import br.com.buscacapital.dto.CategoriaDTO;
import br.com.buscacapital.model.Categoria;

@Controller
public class CategoriaWs {

	private static final Logger log = Logger.getLogger(CategoriaWs.class);
	
	@Autowired
	private CategoriaBO categoriaBO;
	
	@RequestMapping(value="/categorias", method=RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<String> retornaCategoriaJson() {
		Gson gsonCat = new GsonBuilder().create();
		log.info("Obtendo lista de categorias");
		
		List<Categoria> listaCategoria = new ArrayList<Categoria>(this.categoriaBO.listarTodos());
		
		List<CategoriaDTO> listaRetorno = this.montarListaCategotrias(listaCategoria);
		
		String objRetorno = gsonCat.toJson(listaRetorno);
		
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		return new ResponseEntity<String>(objRetorno, responseHeaders, HttpStatus.OK);
	}

	private List<CategoriaDTO> montarListaCategotrias(List<Categoria> listaCategoria) {
		List<CategoriaDTO> listaCategoriaDTO = new ArrayList<CategoriaDTO>();
		
		if (!listaCategoria.isEmpty()) {
			for (Categoria item : listaCategoria) {
				CategoriaDTO cat = new CategoriaDTO();
				
				cat.setIdCategoria(item.getCodigo());
				cat.setNome(item.getNome());
				
				listaCategoriaDTO.add(cat);
			}
		}
		return listaCategoriaDTO;
	}
}
