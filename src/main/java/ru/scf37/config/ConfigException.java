package ru.scf37.config;

import java.util.Properties;

import ru.scf37.config.impl.prop.PropertiesConfigBuilder;

/**
 * Exception thrown by Config library in case of any problems
 * 
 * @author scf37
 *
 */
public class ConfigException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(String message) {
		super(message);
		
		ConfigReader<Properties> reader = ConfigFactory.readPropertiesFrom("classpath:").build();
		reader.read(null, null, null, "my.properties");
		
	}
	
	

}
