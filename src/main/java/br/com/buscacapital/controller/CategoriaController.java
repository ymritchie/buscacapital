package br.com.buscacapital.controller;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.buscacapital.util.Constantes;

@Scope("session")
@Component("categoriaController")
public class CategoriaController {
	
	private static final String FW_MANTER_CATEGORIAS = "/admin/manter-categorias.xhtml" + Constantes.PARAMETRO_JSF_REDIRECT;
	private static Logger log = Logger.getLogger(CategoriaController.class);

	
}
