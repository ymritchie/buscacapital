package br.com.buscacapital.enumerator;

import java.util.ArrayList;
import java.util.List;

public enum TipoPessoa {
	CLIENTE("C", "CLIENTE"), 
	USUARIO("U", "USUÁRIO"),
	FUNCIONARIO("F", "FUNCIONÁRIO");
	
	private String codigo;
	private String descricao;
	
	
	private TipoPessoa(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	public static List<TipoPessoa> listaTipoPessoa (){
		 List<TipoPessoa> lista = new ArrayList<TipoPessoa>();
		 
		 for (TipoPessoa tipo : values()){
			 lista.add(tipo);
		 }
		
		 return lista;
	}
	
	/**
	 * 
	 * @param codigo
	 * @return
	 */
	public boolean isCliente (String codigo){
		for (TipoPessoa tipo : values()){
			if (tipo.codigo.equals(codigo) && codigo.equals("C")){
				return true;
			}
			
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param codigo
	 * @return
	 */
	public boolean isUsuario (String codigo){
		for (TipoPessoa tipo : values()){
			if (tipo.codigo.equals(codigo) && codigo.equals("U")){
				return true;
			}
			
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param codigo
	 * @return
	 */
	public boolean isFuncionario (String codigo){
		for (TipoPessoa tipo : values()){
			if (tipo.codigo.equals(codigo) && codigo.equals("F")){
				return true;
			}
			
		}
		
		return false;
	}
}