package br.com.buscacapital.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buscacapital.model.EntidadeBase;

/**
 * Copmversor genérico para as classe que implementam a EntidadeBase
 * 
 * @author  Yanisley Mora Ritchie
 *
 */
@FacesConverter(value = "entidadeBaseConverter")
public class EntidadeBaseConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {
			return this.getAttributesFrom(component).get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		boolean resultado = false;

		if (value != null && !"".equals(value)) {
			EntidadeBase entity = (EntidadeBase) value;

			// armazena o objeto do tipo EntidadeBase no mapa de atributos.
			resultado = this.addAttribute(component, entity);

			Long codigo = entity.getCodigo();
			if (codigo != null) {
				return String.valueOf(codigo);
			}
		}

		if (resultado) {

			return (String) value;
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param component
	 * @param o
	 * @return
	 */
	protected boolean addAttribute(UIComponent component, EntidadeBase o) {

		String key = new String();
		boolean adicionou = false;

		if (!(o.getCodigo() == null)) {
			key = o.getCodigo().toString();
			this.getAttributesFrom(component).put(key, o);
			adicionou = true;
		}

		return adicionou;
	}
	
	/**
	 * Recupera o mapa de objetos entidadeBase
	 * 
	 * @param component
	 * @return Map<String, Object>
	 */
	protected Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}

}
