package ru.scf37.config.util.servlet;

import javax.servlet.ServletContextEvent;

import org.apache.logging.log4j.core.web.Log4jServletContextListener;

import ru.scf37.config.impl.util.AbstractConfigLog4jInitializationListener;
import ru.scf37.config.impl.util.AbstractLog4jConfigurer;
import ru.scf37.config.util.ConfigLog4j2Configurer;

/**
 * Performs log4j2 initialization for web applications
 * <p>
 * Application name can be specified as 
 * <em>configApplicationName</em> parameter in &lt;context-param&gt; tag in web.xml.
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
