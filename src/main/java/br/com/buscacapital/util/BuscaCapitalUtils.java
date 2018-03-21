package br.com.buscacapital.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;

import org.apache.log4j.Logger;

public class BuscaCapitalUtils {

	private static Logger log = Logger.getLogger(BuscaCapitalUtils.class);
	
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

	public static boolean validarSenha(String senha) {
		if (senha.matches("^.*(?=.{5,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$")) {
			return true;
		} else {
			return false;
		}
		
	}
	
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
	
}
