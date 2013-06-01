package ru.scf37.config.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * Performs log4j initialization for web applications
 * 
 * Application name and version can be specified as 
 * configApplicationName and configApplicationVersion parameters in <context-param> tag in web.xml.
 * 
 * @author scf37
 *
 */
public class ConfigLog4jInitializationListener implements ServletContextListener {
	private static final String CONFIG_APPLICATION_NAME = "configApplicationName";
	private static final String CONFIG_APPLICATION_VERSION = "configApplicationVersion";
	
	private ConfigLog4jConfigurer configurer = new ConfigLog4jConfigurer();
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		String name = (String) context.getInitParameter(CONFIG_APPLICATION_NAME);
		String version = (String) context.getInitParameter(CONFIG_APPLICATION_VERSION);
		
		configurer.configure(name, version);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
