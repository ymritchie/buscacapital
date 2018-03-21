package br.com.buscacapital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_logradouro database table.
 * 
 */
@Entity
@Table(name="tipo_logradouro")
@NamedQuery(name="TipoLogradouro.findAll", query="SELECT t FROM TipoLogradouro t")
public class TipoLogradouro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tipo_logradouro_id")
	private Long codigo;

	@Column(name="nome_tipo_logradouro")
	private String nomeTipoLogradouro;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNomeTipoLogradouro() {
		return nomeTipoLogradouro;
	}

	public void setNomeTipoLogradouro(String nomeTipoLogradouro) {
		this.nomeTipoLogradouro = nomeTipoLogradouro;
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
		TipoLogradouro other = (TipoLogradouro) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoLogradouro [codigo=" + codigo + ", nomeTipoLogradouro=" + nomeTipoLogradouro + "]";
	}
	
	


}