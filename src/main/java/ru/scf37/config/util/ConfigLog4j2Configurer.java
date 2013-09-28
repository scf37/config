package ru.scf37.config.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;
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
		System.setProperty("log4j.configurationFile", file.getAbsolutePath());
	}
}