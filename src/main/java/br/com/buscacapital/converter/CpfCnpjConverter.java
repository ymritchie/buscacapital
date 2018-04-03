package br.com.buscacapital.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buscacapital.util.BCUtils;

@FacesConverter("cpfCnpjConverter")
public class CpfCnpjConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) {
			return "";
		}
		
		if (arg2.toString().length() > 11) {
			return BCUtils.formataCnpj(arg2.toString());
		} else {
			return BCUtils.formataCpf(arg2.toString());
		}
		
		
	}

}
