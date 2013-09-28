package ru.scf37.config.util;

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
	
	@Override
	public AbstractLog4jConfigurer createConfigurer() {
		return new ConfigLog4j2Configurer();
	}

}
