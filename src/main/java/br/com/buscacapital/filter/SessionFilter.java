package br.com.buscacapital.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.buscacapital.model.Usuario;

public class SessionFilter implements Filter {

	private static String FW_PAGINA_LOGIN = "/public/login.jsf";
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		
		
		Usuario usuario = null;
		HttpSession sessao = req.getSession(false);
		
		//Se a sessão não está nula pego o usuário logado
		if (sessao != null) {
			usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		} 
		
		//Se não houver usuário logado
		if (usuario == null) {
			
			String contextPath = req.getContextPath();
			String retornoLogin = contextPath + FW_PAGINA_LOGIN;
			
			//Se Requisição for AJAX
			if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {

				resp.getWriter().printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>", retornoLogin);
			} else {
				resp.sendRedirect(retornoLogin);
			}
			return;
		} else {
			String novaPagina = req.getServletPath();
			  
	        if (sessao.getAttribute("currentPage") == null) {
	            sessao.setAttribute("lastPage", novaPagina);
	            sessao.setAttribute("currentPage", novaPagina);
	        } else {
	  
	            String paginaAnterior = sessao.getAttribute("currentPage").toString();
	            if (!paginaAnterior.equals(novaPagina)) {
	              sessao.setAttribute("lastPage", paginaAnterior);
	              sessao.setAttribute("currentPage", novaPagina);
	            }
	        }
	  
	        chain.doFilter(req, resp);
		}
		  
        
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
