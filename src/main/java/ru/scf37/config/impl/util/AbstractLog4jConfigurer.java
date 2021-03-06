package ru.scf37.config.impl.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import ru.scf37.config.ConfigException;
import ru.scf37.config.ConfigFactory;
import ru.scf37.config.ConfigReader;
import ru.scf37.config.impl.ConfigLog;
import ru.scf37.config.impl.ConfigUtils;
import ru.scf37.config.util.EnvironmentNameResolver;
/**
 * Configurator for log4j.
 * <p>
 * This configurator tries to detect current environment via {@link EnvironmentNameResolver}. 
 * Configuration file names and initialization should be specified in subclasses.
 * 
 * @author scf37
 * @see EnvironmentNameResolver
 */
public abstract class AbstractLog4jConfigurer {
	private EnvironmentNameResolver environmentNameResolver = EnvironmentNameResolver.getDefaultEnvironmentNameResolver();
	
	/**
	 * Set custom EnvironmentNameResolver.
	 * 
	 * @param environmentNameResolver environment name resolver to use
	 */
	public void setEnvironmentNameResolver(
			EnvironmentNameResolver environmentNameResolver) {
		this.environmentNameResolver = environmentNameResolver;
	}

	/**
	 * Configure log4j library.
	 */
	public void configure() {
		String environment = environmentNameResolver.getEnvironmentName();
		
		for (String name: getConfigurationNames()) {
			if (initLog4j(environment, name)) {
				return;
			}	
		}
		
		ConfigLog.warn("log4j configuration not found");
	}
	
	protected abstract List<String> getConfigurationNames();
	
	protected abstract void initLogging(File file);
	
	private boolean initLog4j(String environment, String name) {
		ConfigReader<String> reader = ConfigFactory.readTextFrom("classpath:").build(Charset.forName("UTF-8"));
		
		try {
			String config = reader.read(environment, name);
			if (config == null) {
				ConfigLog.warn("Unable to resolve environment-specific path for " + 
					ConfigUtils.formatAddress(environment, name));
				return false;
			}
			initLog4j0(config, name);
			
			ConfigLog.warn("Log4j initialized successfully from " + 
					ConfigUtils.formatAddress(environment, name));
			return true;
		} catch (ConfigException ex) {
			ConfigLog.warn("Unable to resolve environment-specific path for " + 
					ConfigUtils.formatAddress(environment, name) + ", error: " + ex.getMessage());
		} catch (IOException ex) {
			ConfigLog.warn("Unable to resolve environment-specific path for " + 
					ConfigUtils.formatAddress(environment, name) + ", error: " + ex.getMessage());
		}
		return false;
	}
	
	private void initLog4j0(String config, String suffix) throws IOException {
		FileOutputStream fos = null;
		File tempFile = null;
		try {
			tempFile = File.createTempFile("config-", suffix);
			fos = new FileOutputStream(tempFile);
			fos.write(config.getBytes(Charset.forName("UTF-8")));
			fos.close();
			initLogging(tempFile);
		} finally  {
			if (fos != null) {
				fos.close();
			}
			if (tempFile != null) {
				tempFile.deleteOnExit();
			}
		}
	}

	
}