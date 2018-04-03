package br.com.buscacapital.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;

/**
 * Ferramentas utilitárias do sistema 
 * 
 * @author  Yanisley Mora Ritchie
 * @since 21/03/2018
 *
 */
public class BCUtils {

	private static Logger log = Logger.getLogger(BCUtils.class);
	
	public static final String DIA_MES_ANO = "dd/MM/yyyy";
	
	/**
	 * Criptografa para MD5
	 * @param valor
	 * @return
	 */
	public static String convertStringToMd5(String valor) {
        MessageDigest mDigest;
        try { 
              
               mDigest = MessageDigest.getInstance("MD5");
               
               byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));
               
               
               StringBuffer sb = new StringBuffer();
               for (byte b : valorMD5){
                      sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1,3));
               }

               return sb.toString();
               
        } catch (NoSuchAlgorithmException e) {
        	log.error(e);
            return null;
        } catch (UnsupportedEncodingException e) {
        	log.error(e);
            return null;
        }
	}

	/**
	 * Valida se a senha cumpre com os requisitos mínimos
	 * @param senha
	 * @return
	 */
	public static boolean validarSenha(String senha) {
		if (senha.matches("^.*(?=.{5,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$")) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Verifica se o CPF é valido
	 * @param cpf
	 * @return
	 */
	public static boolean isCpfValido(String cpf) {
	    if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
	    	cpf.equals("22222222222") || cpf.equals("33333333333") ||
	    	cpf.equals("44444444444") || cpf.equals("55555555555") ||
	    	cpf.equals("66666666666") || cpf.equals("77777777777") ||
	    	cpf.equals("88888888888") || cpf.equals("99999999999") ||
	       (cpf.length() != 11))
	       return(false);

	    char dig10, dig11;
	    int sm, i, r, num, peso;

	    try {
	      sm = 0;
	      peso = 10;
	      for (i=0; i<9; i++) {              
	        num = (int)(cpf.charAt(i) - 48); 
	        sm = sm + (num * peso);
	        peso = peso - 1;
	      }

	      r = 11 - (sm % 11);
	      if ((r == 10) || (r == 11))
	         dig10 = '0';
	      else dig10 = (char)(r + 48); 

	      sm = 0;
	      peso = 11;
	      for(i=0; i<10; i++) {
	        num = (int)(cpf.charAt(i) - 48);
	        sm = sm + (num * peso);
	        peso = peso - 1;
	      }

	      r = 11 - (sm % 11);
	      if ((r == 10) || (r == 11))
	         dig11 = '0';
	      else dig11 = (char)(r + 48);

	      if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
	         return(true);
	      else return(false);
	    } catch (InputMismatchException erro) {
	        return(false);
	    }
	}
	
	/*
	 * Verifica se o cnpj é válido
	 */
	public static boolean isCnpjValido(String cnpj) {
		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
		    cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
		    cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
		    cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
		    cnpj.equals("88888888888888") || cnpj.equals("99999999999999") ||
		    (cnpj.length() != 14))
		    return(false);

		    char dig13, dig14;
		    int sm, i, r, num, peso;

		    try {
		      sm = 0;
		      peso = 2;
		      for (i=11; i>=0; i--) {
		        num = (int)(cnpj.charAt(i) - 48);
		        sm = sm + (num * peso);
		        peso = peso + 1;
		        if (peso == 10)
		           peso = 2;
		      }

		      r = sm % 11;
		      if ((r == 0) || (r == 1))
		         dig13 = '0';
		      else dig13 = (char)((11-r) + 48);

		      sm = 0;
		      peso = 2;
		      for (i=12; i>=0; i--) {
		        num = (int)(cnpj.charAt(i)- 48);
		        sm = sm + (num * peso);
		        peso = peso + 1;
		        if (peso == 10)
		           peso = 2;
		      }

		      r = sm % 11;
		      if ((r == 0) || (r == 1))
		         dig14 = '0';
		      else dig14 = (char)((11-r) + 48);

		      if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
		         return(true);
		      else return(false);
		      
		    } catch (InputMismatchException erro) {
		        return(false);
		    }
	}
	
	
	public static String inputStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	/**
	 * Formata telefone com a máscara correspondente para 8, 9, 10 ou 11 números
	 * @param telefone
	 * @return
	 */
	public static String formataTelefone(String telefone) {
		if (telefone != null || !telefone.isEmpty()) {
			Pattern pattern = null;
			Matcher matcher = null;
			
			switch (telefone.length()) {
				case 11:
					pattern = Pattern.compile("([0-9]{2})([0-9]{5})([0-9]{4})");
					matcher = pattern.matcher(telefone);
					return matcher.replaceAll("($1) $2-$3");
				case 10:
					pattern = Pattern.compile("([0-9]{2})([0-9]{4})([0-9]{4})");
					matcher = pattern.matcher(telefone);
					return matcher.replaceAll("($1) $2-$3");
				case 9:
					pattern = Pattern.compile("([0-9]{5})([0-9]{4})");
					matcher = pattern.matcher(telefone);
					return matcher.replaceAll("$1-$2");
				default:
					pattern = Pattern.compile("([0-9]{4})([0-9]{4})");
					matcher = pattern.matcher(telefone);
					return matcher.replaceAll("$1-$2");
			}
			
		}
			
		return telefone;
	}
	
	/**
	 * Formata CPF
	 * @param cpf
	 * @return
	 */
	public static String formataCpf(String cpf) {
		if (cpf != null || !cpf.isEmpty()) {
			Pattern pattern = null;
			Matcher matcher = null;
			
			pattern = Pattern.compile("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})");
			matcher = pattern.matcher(cpf);
			return matcher.replaceAll("$1.$2.$3-$4");
		}
		
		return cpf;
	}
	
	/**
	 * 
	 * @param cnpj
	 * @return
	 */
	public static String formataCnpj(String cnpj) {
		if (cnpj != null || !cnpj.isEmpty()) {
			Pattern pattern = null;
			Matcher matcher = null;
			
			pattern = Pattern.compile("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})");
			matcher = pattern.matcher(cnpj);
			return matcher.replaceAll("$1.$2.$3/$4-$5");
		}
		
		return cnpj;
	}
	
	
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String formataDataPorPadrao(Date data) {
		return formataDataPorPadrao(data, null);
	}
	
	/**
	 * 
	 * @param calendar
	 * @return
	 */
	public static String formataCalendarPorPadrao(Calendar calendar) {
		return formataCalendarPorPadrao(calendar, null);
	}
	
	/**
	 * Retorna a data em formato String conforme o padrão informado ou dd/MM/yyyy se não for informado nenhum
	 * @param data
	 * @param padrao
	 * @return
	 */
	public static String formataDataPorPadrao(Date data, String padrao) {
		if (isStringVazia(padrao)) {
			padrao = DIA_MES_ANO;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(padrao);
		String dataFormatada = sdf.format(data);
		return dataFormatada;
	}
	
	/**
	 * Retorna a data em formato String conforme o padrão informado ou dd/MM/yyyy se não for informado nenhum
	 * @param calendar
	 * @param padrao
	 * @return
	 */
	public static String formataCalendarPorPadrao(Calendar calendar, String padrao) {
		if (isStringVazia(padrao)) {
			padrao = DIA_MES_ANO;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(padrao);
		String dataFormatada = sdf.format(calendar.getTime());
		return dataFormatada;
	}
	
	public static boolean isStringVazia(String obj) {
		return obj == null || "".equals(obj);
	}

	
}
