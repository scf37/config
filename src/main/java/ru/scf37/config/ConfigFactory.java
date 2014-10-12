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
		try {
			return PROP_BUILDER.newInstance().overrideWith(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Factory method use existing java.util.Properties object for configuration.
	 * 
	 * @param properties properties object  
	 * @return builder for Properties config
	 */
	public static PropertiesConfigBuilder readPropertiesFrom(Properties properties) {
		try {
			return new PropertiesConfigBuilder().overrideWith(properties);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Factory method to read plain text configuration.
	 * 
	 * @param url Base url to read from. classpath:, file: and http: urls are supported
	 * @return builder for Text config
	 */
	public static TextConfigBuilder readTextFrom(String url) {
		try {
			return new TextConfigBuilder().or(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static final Class<? extends PropertiesConfigBuilder> PROP_BUILDER;
	private static final Class<? extends TextConfigBuilder> TEXT_BUILDER;
	
	static {
		try {
			String propBuilderClass = System.getProperty("ru.scf37.config.prop_builder_class", "ru.scf37.config.impl.readers.PropertiesConfigBuilder");
			String textBuilderClass = System.getProperty("ru.scf37.config.text_builder_class", "ru.scf37.config.impl.readers.TextConfigBuilder");
			
			PROP_BUILDER = Class.forName(propBuilderClass).asSubclass(PropertiesConfigBuilder.class);
			TEXT_BUILDER = Class.forName(textBuilderClass).asSubclass(TextConfigBuilder.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
