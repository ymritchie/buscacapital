package br.com.buscacapital.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sub_categoria database table.
 * 
 */
@Entity
@Table(name="sub_categoria")
@NamedQuery(name="SubCategoria.findAll", query="SELECT s FROM SubCategoria s")
public class SubCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sub_categoria_id")
	private Long codigo;

	@Column(name="descricao")
	private String descricao;

	@Column(name="nome")
	private String nome;

	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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
		SubCategoria other = (SubCategoria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SubCategoria [codigo=" + codigo + ", descricao=" + descricao + ", nome=" + nome + ", categoria="
				+ categoria + "]";
	}

	

}