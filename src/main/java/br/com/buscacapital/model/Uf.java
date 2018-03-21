package br.com.buscacapital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the uf database table.
 * 
 */
@Entity
@NamedQuery(name="Uf.findAll", query="SELECT u FROM Uf u")
@Table (name = "uf")
public class Uf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="uf_id")
	private String codigo;

	@Column(name="nome_estado")
	private String nomeEstado;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
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
		Uf other = (Uf) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Uf [codigo=" + codigo + ", nomeEstado=" + nomeEstado + "]";
	}

	

}