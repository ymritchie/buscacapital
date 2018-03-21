package br.com.buscacapital.exception;

import javax.faces.application.FacesMessage.Severity;

public class BuscaCapitalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private Severity severity = null;

	public BuscaCapitalException() {
		super();
	}

	public BuscaCapitalException(String message) {
		super(message);
		this.codigo = message;
	}

	public BuscaCapitalException(String message, Severity severity) {
		super(message);
		this.severity = severity;
	}

	public BuscaCapitalException(String codigo, String message) {
		super(message);
		this.codigo = codigo;
	}

	public BuscaCapitalException(String codigo, String message, Throwable cause) {
		super(message, cause);
	}

	public BuscaCapitalException(String message, Throwable cause) {
		super(message, cause);
	}

	public BuscaCapitalException(Throwable cause) {
		super(cause);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
}
