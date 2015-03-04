package ru.scf37.config;

import java.util.Properties;

import ru.scf37.config.impl.readers.PropertiesConfigBuilder;
import ru.scf37.config.impl.readers.TextConfigBuilder;

/**
 * Main entry point to Config library.
 * 
 * @author scf37
 *
 */
public final class ConfigFactory {
	private ConfigFactory() {
	}
	
	/**
	 * Factory method to read configuration of type java.util.Properties.
	 * 
	 * @param url Base url to read from. classpath:, file: and http: urls are supported. You can also just pass local FS file path. 
	 * @return builder for Properties config
	 */
	public static PropertiesConfigBuilder readPropertiesFrom(String url) {
		return new PropertiesConfigBuilder().overrideWith(url);
	}
	
	/**
	 * Factory method use existing java.util.Properties object for configuration.
	 * 
	 * @param properties properties object  
	 * @return builder for Properties config
	 */
	public static PropertiesConfigBuilder readPropertiesFrom(Properties properties) {
		return new PropertiesConfigBuilder().overrideWith(properties);
	}
	
	/**
	 * Factory method to read plain text configuration.
	 * 
	 * @param url Base url to read from. classpath:, file: and http: urls are supported
	 * @return builder for Text config
	 */
	public static TextConfigBuilder readTextFrom(String url) {
		return new TextConfigBuilder().or(url);
	}
}
