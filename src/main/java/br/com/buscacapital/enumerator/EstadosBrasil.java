package br.com.buscacapital.enumerator;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public enum EstadosBrasil {
	AC("AC","Acre"),
	AL("AL","Alagoas"),
	AP("AP","Amapá"),
	AM("AM","Amazonas"),
	BA("BA","Bahia"),
	CE("CE","Ceará"),
	DF("DF","Distrito Federal"),
	ES("ES","Espírito Santo"),
	GO("GO","Goiás"),
	MA("MA","Maranhão"),
	MT("MT","Mato Grosso"),
	MS("MS","Mato Grosso do Sul"),
	MG("MG","Minas Gerais"),
	PA("PA","Pará"),
	PB("PB","Paraíba"),
	PR("PR","Paraná"),
	PE("PE","Pernambuco"),
	PI("PI","Piauí"),
	RJ("RJ","Rio de Janeiro"),
	RN("RN","Rio Grande do Norte"),
	RS("RS","Rio Grande do Sul"),
	RO("RO","Rondônia"),
	RR("RR","Roraima"),
	SC("SC","Santa Catarina"),
	SP("SP","São Paulo"),
	SE("SE","Sergipe"),
	TO("TO","Tocantins");
	
	private String codigo;
	private String nome;
	
	
	private EstadosBrasil(String codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public static List<SelectItem> listaElementoEnum(){
		List<SelectItem> listaElementos = new ArrayList<SelectItem>();
		
		for (EstadosBrasil estado : values()) {
			listaElementos.add(new SelectItem(estado.getCodigo(), estado.getNome()));
		}
		
		return listaElementos;
	}

}
