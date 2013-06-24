package ru.scf37.config;

import ru.scf37.config.impl.prop.PropertiesConfigBuilder;
import ru.scf37.config.impl.text.TextConfigBuilder;

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
	 * @param url Base url to read from. classpath:, file: and http: urls are supported
	 * @return builder for Properties config
	 */
	public static PropertiesConfigBuilder readPropertiesFrom(String url) {
		try {
			return PROP_BUILDER.newInstance().append(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static PropertiesConfigBuilder readSystemProperties() {
		try {
			return PROP_BUILDER.newInstance().appendSystemProperties();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Factory method to read plain text configuration
	 * 
	 * @param url Base url to read from. classpath:, file: and http: urls are supported
	 * @return builder for Text config
	 */
	public static TextConfigBuilder readTextFrom(String url) {
		try {
			return TEXT_BUILDER.newInstance().or(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static final Class<PropertiesConfigBuilder> PROP_BUILDER;
	private static final Class<TextConfigBuilder> TEXT_BUILDER;
	
	static {
		try {
			String propBuilderClass = System.getProperty("ru.scf37.config.prop_builder_class", "ru.scf37.config.impl.prop.PropertiesConfigBuilder");
			String textBuilderClass = System.getProperty("ru.scf37.config.text_builder_class", "ru.scf37.config.impl.text.TextConfigBuilder");
			
			PROP_BUILDER = (Class<PropertiesConfigBuilder>) Class.forName(propBuilderClass);
			TEXT_BUILDER = (Class<TextConfigBuilder>) Class.forName(textBuilderClass);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
