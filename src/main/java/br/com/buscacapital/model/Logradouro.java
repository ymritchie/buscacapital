package br.com.buscacapital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the logradouro database table.
 * 
 */
@Entity
@NamedQuery(name="Logradouro.findAll", query="SELECT l FROM Logradouro l")
@Table (name = "logradouro")
public class Logradouro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="logradouro_id")
	private Long codigo;

	@Column(name="nom_logradouro")
	private String nomLogradouro;

	@Column(name="num_CEP")
	private Long numCep;

	@ManyToOne
	@JoinColumn(name="bairro_id")
	private Bairro bairro;

	@ManyToOne
	@JoinColumn(name="tipo_logradouro_id")
	private TipoLogradouro tipoLogradouro;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNomLogradouro() {
		return nomLogradouro;
	}

	public void setNomLogradouro(String nomLogradouro) {
		this.nomLogradouro = nomLogradouro;
	}

	public Long getNumCep() {
		return numCep;
	}

	public void setNumCep(Long numCep) {
		this.numCep = numCep;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public TipoLogradouro getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
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
		Logradouro other = (Logradouro) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Logradouro [codigo=" + codigo + ", nomLogradouro=" + nomLogradouro + ", numCep=" + numCep + ", bairro="
				+ bairro + ", tipoLogradouro=" + tipoLogradouro + "]";
	}
	
	


}