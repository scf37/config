package ru.scf37.config.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.Log4jConfigurer;

import ru.scf37.config.impl.util.AbstractLog4jConfigurer;
/**
 * Configurator for log4j.
 * <p/>
 * This configurator tries to detect current environment via {@link EnvironmentNameResolver}. 
 * It supports both log4j.xml and log4j.properties configuration files.
 * 
 * @author scf37
 * @see EnvironmentNameResolver
 */
public class ConfigLog4jConfigurer extends AbstractLog4jConfigurer {

	@Override
	protected List<String> getConfigurationNames() {
		return Arrays.asList(new String[]{"log4j.xml", "log4j.properties"});
	}

	@Override
	protected void initLogging(File file) {
		try {
			Log4jConfigurer.initLogging(file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}