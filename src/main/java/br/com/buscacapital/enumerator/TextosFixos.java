package br.com.buscacapital.enumerator;

public enum TextosFixos {
	TERMO_DE_USO("<h2>TERMO DE USO - BUSCACAPITAL</h2><p class='red'><strong>Claúsula Única:</strong></p><ul><li>Você não é obrigado a concordar, mas se clicou já era.</li></ul>");
	
	private String texto;
	
	private TextosFixos(String texto) {
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
}
