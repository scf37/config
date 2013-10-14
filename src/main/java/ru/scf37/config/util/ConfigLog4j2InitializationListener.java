package ru.scf37.config.util;

import javax.servlet.ServletContextEvent;

import org.apache.logging.log4j.core.web.Log4jServletContextListener;

/**
 * Performs log4j2 initialization for web applications
 * 
 * Application name and version can be specified as 
 * configApplicationName and configApplicationVersion parameters in <context-param> tag in web.xml.
 * 
 * @author scf37
 *
 */
public class ConfigLog4j2InitializationListener extends AbstractConfigLog4jInitializationListener {
	private Log4jServletContextListener log4jServletContextListener = new  Log4jServletContextListener();
	@Override
	public AbstractLog4jConfigurer createConfigurer() {
		return new ConfigLog4j2Configurer();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		log4jServletContextListener.contextInitialized(sce);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log4jServletContextListener.contextDestroyed(sce);
	}

}
