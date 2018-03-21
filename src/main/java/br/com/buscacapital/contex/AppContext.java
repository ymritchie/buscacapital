package br.com.buscacapital.contex;

import org.springframework.context.ApplicationContext;

public class AppContext {

	private static ApplicationContext applicationContext;

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext 
	 */
	public static void setApplicationContext(ApplicationContext applicationContext) {
		AppContext.applicationContext = applicationContext;
	}

}