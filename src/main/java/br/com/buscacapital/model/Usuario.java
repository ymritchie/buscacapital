package br.com.buscacapital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * Classe modelo para mapear a entidade de persistência usuario 
 * 
 * @author  Yanisley Mora Ritchie
 * @since 21/03/2018
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u"),
	@NamedQuery(name="Usuario.findByLoginSenha", query="SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha"),
	@NamedQuery(name="Usuario.findByLogin", query="SELECT u FROM Usuario u WHERE u.login = :login"),
	@NamedQuery(name="Usuario.findByEmail", query="SELECT u FROM Usuario u WHERE u.email = :email")
})
@Table (name ="usuario")
public class Usuario implements Serializable, EntidadeBase {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="usuario_id")
	private Long codigo;
	
	@Column(name="login")
	private String login;
	
	@Column(name="senha")
	private String senha;

	@Column(name="nome")
	private String nome;
	
	@Column(name="sobrenome")
	private String sobrenome;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_nascimento")
	private Date dataNascimento;

	@Column(name="sexo")
	private String sexo;

	@Column(name="email")
	private String email;

	@Column(name="telefone")
	private String telefone;
	
	@Column(name="administrador")
	private Boolean administrador;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public Boolean getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [codigo=" + codigo + ", login=" + login + ", senha=" + senha + ", nome=" + nome + ", sobrenome="
				+ sobrenome + ", dataNascimento=" + dataNascimento + ", sexo=" + sexo + ", email=" + email
				+ ", telefone=" + telefone + ", adminisrador=" + administrador + "]";
	}

	

	

}