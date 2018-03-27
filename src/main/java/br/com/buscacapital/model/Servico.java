package br.com.buscacapital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Classe modelo para mapear a entidade de persistência servico 
 * 
 * @author  Yanisley Mora Ritchie
 * @since 21/03/2018
 *
 */
@Entity
@NamedQuery(name="Servico.findAll", query="SELECT s FROM Servico s")
@Table (name ="servico")
public class Servico implements Serializable, EntidadeBase {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="servico_id")
	private Long codigo;

	@Column(name="descricao")
	private String descricao;

	@Column(name="preco")
	private Double preco;

	@Column(name="nome")
	private String nome;

	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;

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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		Servico other = (Servico) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Servico [codigo=" + codigo + ", descricao=" + descricao + ", preco=" + preco + ", nome="
				+ nome + ", categoria=" + categoria + ", cliente=" + cliente + "]";
	}

	
}