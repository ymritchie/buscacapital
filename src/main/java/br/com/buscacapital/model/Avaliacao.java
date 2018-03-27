package br.com.buscacapital.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Classe modelo para mapear a entidade de persistência avaliacao 
 * 
 * @author  Yanisley Mora Ritchie
 * @since 21/03/2018
 *
 */
@Entity
@NamedQuery(name="Avaliacao.findAll", query="SELECT a FROM Avaliacao a")
@Table(name = "avaliacao")
public class Avaliacao implements Serializable, EntidadeBase {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="avaliacao_id")
	private Long codigo;

	@Column(name="ranking")
	private Integer ranking;

	@Column(name="texo")
	private String textoAvaliacao;

	//bi-directional many-to-one association to Pedido
	@ManyToOne
	@JoinColumn(name="pedido_id")
	private Pedido pedido;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getTextoAvaliacao() {
		return textoAvaliacao;
	}

	public void setTextoAvaliacao(String textoAvaliacao) {
		this.textoAvaliacao = textoAvaliacao;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Avaliacao other = (Avaliacao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Avaliacao [codigo=" + codigo + ", ranking=" + ranking + ", textoAvaliacao=" + textoAvaliacao
				+ ", pedido=" + pedido + "]";
	}
	
	

	
}