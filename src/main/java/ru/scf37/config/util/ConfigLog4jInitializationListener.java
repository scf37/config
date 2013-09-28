package ru.scf37.config.util;

/**
 * Performs log4j initialization for web applications
 * 
 * Application name and version can be specified as 
 * configApplicationName and configApplicationVersion parameters in <context-param> tag in web.xml.
 * 
 * @author scf37
 *
 */
public class ConfigLog4jInitializationListener extends AbstractConfigLog4jInitializationListener {
	
	@Override
	public AbstractLog4jConfigurer createConfigurer() {
		return new ConfigLog4jConfigurer();
	}

}
