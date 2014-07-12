package ru.scf37.config.util.servlet;

import ru.scf37.config.impl.util.AbstractConfigLog4jInitializationListener;
import ru.scf37.config.impl.util.AbstractLog4jConfigurer;
import ru.scf37.config.util.ConfigLog4jConfigurer;

/**
 * Performs log4j initialization for web applications
 * 
 * Application name can be specified as 
 * <em>configApplicationName</em> parameter in &lt;context-param&gt; tag in web.xml.
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
