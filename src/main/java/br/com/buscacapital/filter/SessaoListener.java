package br.com.buscacapital.filter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Classe listener que controla a Sessão
 * 
 * @author  Yanisley Mora Ritchie
 *
 */
public class SessaoListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) { 
		String primeirpAcesso = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date(event.getSession().getCreationTime()));
        System.out.println("Sessão criada: " + event.getSession().getId() + ". Data de criação: " + primeirpAcesso) ;
   }

   public void sessionDestroyed(HttpSessionEvent event) {     
        String ultimoAcesso = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date(event.getSession().getLastAccessedTime()));
        System.out.println("Sessão expirada "+event.getSession().getId()+". Ultimo Acesso = "+ ultimoAcesso);
   }

	
}
