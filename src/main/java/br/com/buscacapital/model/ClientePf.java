package br.com.buscacapital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * Classe modelo para mapear a entidade de persistência clientepf 
 * 
 * @author  Yanisley Mora Ritchie
 * @since 21/03/2018
 *
 */
@Entity
@Table(name="cliente_pf")
@NamedQuery(name="ClientePf.findAll", query="SELECT c FROM ClientePf c")
public class ClientePf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="clientepf_id")
	private Long codigo;

	@Column(name="cpf")
	private Long cpf;

	@Temporal(TemporalType.DATE)
	@Column(name="data_nascimento")
	private Date dataNascimento;

	@Column(name="email_principal")
	private String emailPrincipal;

	@Column(name="email_secundario")
	private String emailSecundario;

	@Column(name="nome")
	private String nome;

	@Column(name="sobrenome")
	private String sobrenome;

	@Column(name="telefone_principal")
	private String telefonePrincipal;

	@Column(name="telefone_secundario")
	private String telefoneSecundario;

	@OneToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmailPrincipal() {
		return emailPrincipal;
	}

	public void setEmailPrincipal(String emailPrincipal) {
		this.emailPrincipal = emailPrincipal;
	}

	public String getEmailSecundario() {
		return emailSecundario;
	}

	public void setEmailSecundario(String emailSecundario) {
		this.emailSecundario = emailSecundario;
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

	public String getTelefonePrincipal() {
		return telefonePrincipal;
	}

	public void setTelefonePrincipal(String telefonePrincipal) {
		this.telefonePrincipal = telefonePrincipal;
	}

	public String getTelefoneSecundario() {
		return telefoneSecundario;
	}

	public void setTelefoneSecundario(String telefoneSecundario) {
		this.telefoneSecundario = telefoneSecundario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		ClientePf other = (ClientePf) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClientePf [codigo=" + codigo + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento
				+ ", emailPrincipal=" + emailPrincipal + ", emailSecundario=" + emailSecundario + ", nome=" + nome
				+ ", sobrenome=" + sobrenome + ", telefonePrincipal=" + telefonePrincipal + ", telefoneSecundario="
				+ telefoneSecundario + ", cliente=" + cliente + "]";
	}

	

}