package br.com.buscacapital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * Classe modelo para mapear a entidade de persistência clientepj 
 * 
 * @author  Yanisley Mora Ritchie
 * @since 21/03/2018
 *
 */
@Entity
@Table(name="cliente_pj")
@NamedQuery(name="ClientePj.findAll", query="SELECT c FROM ClientePj c")
public class ClientePj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="clientepj_id")
	private Long codigo;

	@Column(name="cnpj")
	private Long cnpj;

	@Temporal(TemporalType.DATE)
	@Column(name="data_inscricao")
	private Date dataInscricao;

	@Column(name="email_principal")
	private String emailPrincipal;

	@Column(name="email_secundario")
	private String emailSecundario;

	@Column(name="nome_fantasia")
	private String nomeFantasia;

	@Column(name="razao_social")
	private String razaoSocial;

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

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDataInscricao() {
		return dataInscricao;
	}

	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
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

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
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
		ClientePj other = (ClientePj) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClientePj [codigo=" + codigo + ", cnpj=" + cnpj + ", dataInscricao=" + dataInscricao
				+ ", emailPrincipal=" + emailPrincipal + ", emailSecundario=" + emailSecundario + ", nomeFantasia="
				+ nomeFantasia + ", razaoSocial=" + razaoSocial + ", telefonePrincipal=" + telefonePrincipal
				+ ", telefoneSecundario=" + telefoneSecundario + ", cliente=" + cliente + "]";
	}
	
	

	
}