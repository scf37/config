package ru.scf37.config.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import ru.scf37.config.ConfigException;
import ru.scf37.config.ConfigFactory;
import ru.scf37.config.ConfigReader;
import ru.scf37.config.impl.ConfigUtils;
/**
 * Configurator for log4j.
 * <p/>
 * This configurator tries to detect current environment via {@link EnvironmentNameResolver}. 
 * Configuration file names and initialization should be specified in subclasses
 * 
 * @author scf37
 * @see EnvironmentNameResolver
 */
public abstract class AbstractLog4jConfigurer {
	private EnvironmentNameResolver environmentNameResolver = new EnvironmentNameResolver();
	
	/**
	 * Set custom EnvironmentNameResolver.
	 * 
	 * @param environmentNameResolver
	 */
	public void setEnvironmentNameResolver(
			EnvironmentNameResolver environmentNameResolver) {
		this.environmentNameResolver = environmentNameResolver;
	}

	/**
	 * Configure log4j library.
	 * 
	 * @param app application name
	 * @param version application version
	 */
	public void configure(String app, String version) {
		String environment = environmentNameResolver.getEnvironmentName();
		
		for (String name: getConfigurationNames()) {
			if (initLog4j(app, version, environment, name)) {
				return;
			}	
		}
		
		System.err.println("log4j configuration not found");
	}
	
	protected abstract List<String> getConfigurationNames();
	
	protected abstract void initLogging(File file);
	
	private boolean initLog4j(String application, String version, String environment, String name) {
		ConfigReader<String> reader = ConfigFactory.readTextFrom("classpath:").build(Charset.forName("UTF-8"));
		
		try {
			String config = reader.read(application, version, environment, name);
			if (config == null) {
				System.err.println("Unable to resolve environment-specific path for " + 
					ConfigUtils.formatAddress(application, version, environment, name));
				return false;
			}
			initLog4j(config, name);
			
			System.out.println("Log4j initialized successfully from " + 
					ConfigUtils.formatAddress(application, version, environment, name));
			return true;
		} catch (ConfigException ex) {
			System.err.println("Unable to resolve environment-specific path for " + 
					ConfigUtils.formatAddress(application, version, environment, name) + ", error: " + ex.getMessage());
		} catch (IOException ex) {
			System.err.println("Unable to resolve environment-specific path for " + 
					ConfigUtils.formatAddress(application, version, environment, name) + ", error: " + ex.getMessage());
		}
		return false;
	}
	
	private void initLog4j(String config, String loc) throws IOException {
		FileOutputStream fos = null;
		File tempFile = null;
		try {
			tempFile = File.createTempFile("config-", loc);
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