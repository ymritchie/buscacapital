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
@PrimaryKeyJoinColumn(name="cliente_id")
public class ClientePj extends Cliente {
	

	@Column(name="cnpj")
	private String cnpj;

	@Temporal(TemporalType.DATE)
	@Column(name="data_inscricao")
	private Date dataInscricao;

	@Column(name="nome_fantasia")
	private String nomeFantasia;

	@Column(name="razao_social")
	private String razaoSocial;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDataInscricao() {
		return dataInscricao;
	}

	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
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

	@Override
	public String toString() {
		return super.toString() + "ClientePj [cnpj=" + cnpj + ", dataInscricao=" + dataInscricao + ", nomeFantasia=" + nomeFantasia
				+ ", razaoSocial=" + razaoSocial + "]";
	}
	
	
	
}