package ru.scf37.config;

import ru.scf37.config.impl.prop.PropertiesConfigBuilder;
import ru.scf37.config.impl.str.StringConfigBuilder;

/**
 * Main entry point to Config library.
 * 
 * @author scf37
 *
 */
public class ConfigFactory {
	/**
	 * Factory method to read configuration of type java.util.Properties.
	 * 
	 * @param url Base url to read from. classpath:, file: and http: urls are supported
	 * @return builder for Properties config
	 */
	public static PropertiesConfigBuilder readPropertiesFrom(String url) {
		return new PropertiesConfigBuilder().append(url);
	}
	
	public static PropertiesConfigBuilder readSystemProperties() {
		return new PropertiesConfigBuilder().appendSystemProperties();
	}
	
	/**
	 * Factory method to read plain String configuration
	 * 
	 * @param url Base url to read from. classpath:, file: and http: urls are supported
	 * @return builder for String config
	 */
	public static StringConfigBuilder readStringFrom(String url) {
		return new StringConfigBuilder().or(url);
	}
}
