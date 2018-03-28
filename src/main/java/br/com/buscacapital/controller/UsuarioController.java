package br.com.buscacapital.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.buscacapital.bo.ClienteBO;
import br.com.buscacapital.bo.UsuarioBO;
import br.com.buscacapital.contex.SessionContext;
import br.com.buscacapital.model.Cliente;
import br.com.buscacapital.model.Usuario;
import br.com.buscacapital.util.BCUtils;
import br.com.buscacapital.util.Mensagens;

/**
 * Controlador das requisições que ter a ver com <code>Usuario</code>
 * 
 * @author Yanisley Mora Ritchie
 *
 */
@Scope("session")
@Component("usuarioController")
public class UsuarioController {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(UsuarioController.class);
	
	private static String FW_PAGINA_PRINCIPAL = "/index.xhtml?faces-redirect=true";
	
	private static String FW_PAGINA_LOGIN = "/public/login.xhtml?faces-redirect=true";
	
	private static String FW_PAGINA_REDEFINIR_SENHA = "/public/recuperar-senha.xhtml?faces-redirect=true";
	
	private static String FW_PAGINA_REGISTRAR_USUARIO = "/public/registrar-usuario.xhtml?faces-redirect=true";
	
	private static String FW_PAGINA_CONCLUIR_REGISTRO_USUARIO= "/public/concluir-registro-usuario.xhtml?faces-redirect=true";
	
	private static String FW_PAGINA_ADMIN = "/admin/main.xhtml?faces-redirect=true";
	
	private static String FW_PAGINA_MANTER_USUARIO = "/admin/manter-usuario.xhtml?faces-redirect=true";
	 
	@Autowired
	private UsuarioBO usuarioBO;
	
	@Autowired
	private ClienteBO clienteBO;
	
	private String login;
	
	private String senha;
	
	private String verificaSenha;
	
	private Usuario usuario;
	
	private List<Usuario> listaUsuario;
	
	private boolean pesquisaUsuario;
	 
	private Date dataMinimaCadastro;
	
	
	public String iniciarManterUsuario() {
		this.pesquisaUsuario = true;
		this.listaUsuario = new ArrayList<Usuario>(this.usuarioBO.listarTodos());
		return FW_PAGINA_MANTER_USUARIO;
	}
	
	public Usuario getUsuarioLogado () {
		return (Usuario) SessionContext.getInstance().getUsuarioLogado();
	}
	
	/**
	 * Login no sistema
	 * 
	 * @author  Yanisley Mora Ritchie
	 * @return
	 */
	public String login() {
		try {
            this.usuario = this.usuarioBO.buscarPorLoginSenha(this.login, this.senha);
  
            if (this.usuario == null) {
              Mensagens.addMsgErro("Login ou Senha errado, tente novamente !");
              //FacesContext.getCurrentInstance().validationFailed();
              return "";
            } else {
            	if (!this.usuario.getAtivo()) {
            		 Mensagens.addMsgErro("Usuário Inativo. Entre em contato para ativar seu usuário !");
            		 return "";
            	}
            }
  
            SessionContext.getInstance().setAttribute("usuarioLogado", this.usuario);
            
            if (usuario.getAdministrador()) {
            	return FW_PAGINA_ADMIN;
            } else {
            	Cliente cliente = this.clienteBO.buscarClientePorUsuario(this.usuario);
            	
            	if (cliente != null) {
            		SessionContext.getInstance().setAttribute("cliente", cliente);
            		return FW_PAGINA_ADMIN;
            	} 
            	
            	return FW_PAGINA_PRINCIPAL;
            }
            
        } catch (Exception e) {
        	log.error(e.getMessage());
        	Mensagens.addMsgErro(e.getMessage());
            //FacesContext.getCurrentInstance().validationFailed();
            return "";
        }
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @return
	 */
	public String logout() {
		SessionContext.getInstance().encerrarSessao();
		return FW_PAGINA_PRINCIPAL;
	}
	
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @param email
	 */
	public void solicitarNovaSenha(String email) {
        try {
        	this.usuarioBO.gerarNovaSenha(email);
        } catch (Exception e) {
        	log.error(e.getMessage());
        	Mensagens.addMsgInfo(e.getMessage());
        	FacesContext.getCurrentInstance().validationFailed();
        }
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @return
	 */
	public String redefinirSenha() {
		return FW_PAGINA_REDEFINIR_SENHA;
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @return
	 */
	public String cadastrarNovoUsuario() {
		this.usuario = new Usuario();
		return FW_PAGINA_REGISTRAR_USUARIO;
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * 
	 * @return
	 */
	public String registroInicialUsuario() {
		if (this.usuario == null) {
			Mensagens.addMsgErro("Dados não encontrados!");
			return "";
		} else {
			//Verificar se o login foi preenchido
			if (this.usuario.getLogin() != null || !this.usuario.getLogin().isEmpty()) {
				//verifica se o login já existe no banco de dados
				if (this.usuarioBO.buscarPorLogin(this.usuario.getLogin())) {
					Mensagens.addMsgErro("Usuário registrado no sistema, escolha outro login!");
					return "";
				} 
			} else {
				Mensagens.addMsgErro("Favor preencha o login antes de continuar!");
				return "";
			}
			
			//Verificar se o email foi preenchido e se já existe no banco de dados
			if (this.usuario.getEmail() != null || !this.usuario.getEmail().isEmpty()) {
				if (this.usuarioBO.buscarPorEmail(this.usuario.getEmail())) {
					Mensagens.addMsgErro("E-mail registrado no sistema, tente recuperar sua senha ou use outro e-mail para fazer o registro!!");
					return "";
				} 
			} else {
				Mensagens.addMsgErro("Favor preencha o e-mail antes de continuar!");
				return "";
			}
			
			
		}
		
		return FW_PAGINA_CONCLUIR_REGISTRO_USUARIO;
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @since 16/03/2018
	 * @return
	 */
	public String cancelarInclusaoUsuario() {
		return FW_PAGINA_PRINCIPAL;
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * 
	 */
	public String salvarUsuario() {
		if (!BCUtils.validarSenha(this.usuario.getSenha())) {
			Mensagens.addMsgErro("A Senha não cumpre com os requisitos mínimos.");
			return "";
		}
		
		try {
			this.usuarioBO.salvarUsuario(this.usuario);
			Mensagens.addMsgInfo("Usuário cadastrado com sucesso!");
		} catch (Exception e) {
			log.error(e);
			Mensagens.addMsgErro(e.getMessage());
			return "";
		}
		
		return FW_PAGINA_LOGIN;
	}
	
	/**
	 * @author  Yanisley Mora Ritchie
	 * @return
	 */
	public String fazerLogin() {
		return FW_PAGINA_LOGIN;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public String getVerificaSenha() {
		return verificaSenha;
	}

	public void setVerificaSenha(String verificaSenha) {
		this.verificaSenha = verificaSenha;
	}

	public Date getDataMinimaCadastro() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 18);
		
		this.dataMinimaCadastro = cal.getTime();
		
		return dataMinimaCadastro;
	}

	public void setDataMinimaCadastro(Date dataMinimaCadastro) {
		this.dataMinimaCadastro = dataMinimaCadastro;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public boolean isPesquisaUsuario() {
		return pesquisaUsuario;
	}

	public void setPesquisaUsuario(boolean pesquisaUsuario) {
		this.pesquisaUsuario = pesquisaUsuario;
	}
	
	
	
	
	
}
