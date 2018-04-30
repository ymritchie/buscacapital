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
		
		Map<Long, String> categorias = new HashMap<Long, String>();
		
		for (Categoria cat : listaCategoria) {
			categorias.put(cat.getCodigo(), cat.getNome());
		}
		
		String objRetorno = gsonCat.toJson(categorias);
		
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		return new ResponseEntity<String>(objRetorno, responseHeaders, HttpStatus.OK);
	}
}
