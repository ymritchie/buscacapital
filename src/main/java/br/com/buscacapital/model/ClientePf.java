package br.com.buscacapital.model;

import javax.persistence.*;
import java.util.Date;


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
@PrimaryKeyJoinColumn(name="cliente_id")
public class ClientePf extends Cliente {
	
	@Column(name="cpf")
	private String cpf;

	@Temporal(TemporalType.DATE)
	@Column(name="data_nascimento")
	private Date dataNascimento;

	@Column(name="nome")
	private String nome;

	@Column(name="sobrenome")
	private String sobrenome;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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

	@Override
	public String toString() {
		return super.toString() + "ClientePf [cpf=" + cpf + ", dataNascimento=" + dataNascimento + ", nome=" + nome + ", sobrenome="
				+ sobrenome + "]";
	}

	
	

}