package ru.scf37.config.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.impl.Log4jContextFactory;
import org.apache.logging.log4j.core.selector.ContextSelector;
/**
 * Configurator for log4j2.
 * <p/>
 * This configurator tries to detect current environment via {@link EnvironmentNameResolver}. 
 * It supports both log4j.xml and log4j.properties configuration files.
 * 
 * @author scf37
 * @see EnvironmentNameResolver
 */
public class ConfigLog4j2Configurer extends AbstractLog4jConfigurer {

	@Override
	protected List<String> getConfigurationNames() {
		return Arrays.asList(new String[]{"log4j2-test.json", "log4j2-test.jsn", "log4j2-test.xml", 
				"log4j2.json", "log4j2.jsn", "log4j2.xml"});
	}

	@Override
	protected void initLogging(File file) {
		System.setProperty("log4j.configurationFile", file.toURI().toString());
		//re-initialize log4j2
		ContextSelector selector = ((Log4jContextFactory)LogManager.getFactory()).getSelector();
		for (LoggerContext ctx: new ArrayList<LoggerContext>((selector.getLoggerContexts()))) {
			selector.removeContext(ctx);
		}
	}
}