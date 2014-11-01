package ru.scf37.config.impl.readers;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ru.scf37.config.ConfigReader;
import ru.scf37.config.impl.UrlConfigReader;
/**
 * 
 * Builder for Properties configuration.
 * <p>
 * Properties configurations support merging, thus next configurations 
 * will add properties overriding already defined keys.
 * 
 * @author scf37
 *
 */
public class PropertiesConfigBuilder {
	private List<ConfigReader<InputStream>> readers = new ArrayList<ConfigReader<InputStream>>();
	
	public PropertiesConfigBuilder() {
	}
	/**
	 * Append new configuration source.
	 * <p>
	 * Properties from this source will be added to result, overriding already defined properties.
	 * 
	 * @param url file:, classpath: or http: url
	 * @return this builder
	 */
	public final PropertiesConfigBuilder overrideWith(String url) {
		readers.add(makeConfigReader(url));
		return this;
	}
	
	/**
	 * Append existing Properties object.
	 * <p>
	 * Properties from this source will be added to result, overriding already defined properties.
	 * 
	 * @param properties existing Properties object to use
	 * @return this builder
	 */
	public final PropertiesConfigBuilder overrideWith(Properties properties) {
		readers.add(makePropertiesReader(properties));
		return this;
	}

	/**
	 * Append System properties.
	 * <p>
	 * Properties from this source will be added to result, overriding already defined properties.
	 * 
	 * @return this builder
	 */	
	public final PropertiesConfigBuilder overrideWithSystemProperties() {
		readers.add(makeSystemPropertiesReader());
		return this;
	}
	
	public final PropertiesConfigBuilder overrideWithEnvironmentVariables() {
		readers.add(makeEnvironmentVariablesReader());
		return this;
	}
	
	protected ConfigReader<InputStream> makeEnvironmentVariablesReader() {
		Properties p = new Properties();
		p.putAll(System.getenv());
		return new PropertiesObjectReader(p);
	}
	
	protected ConfigReader<InputStream> makeSystemPropertiesReader() {
		return new PropertiesObjectReader(System.getProperties());
	}
	
	protected ConfigReader<InputStream> makePropertiesReader(Properties properties) {
		return new PropertiesObjectReader(properties);
	}
	
	protected ConfigReader<InputStream> makeConfigReader(String url) {
		return new UrlConfigReader(url);
	}
	/**
	 * Builds ConfigReader which can be used to read Properties configuration. 
	 * 
	 * @return ConfigReader
	 */
	public final ConfigReader<Properties> build() {
		return new PropertiesAppendingReader(readers);
	}
	
	public final <T> ConfigReader<T> buildAnnotationReader(Class<T> clazz) {
		return new AnnotationReader<T>(clazz, 
				new PropertiesAppendingReader(readers),
				new TextReader(readers, Charset.forName("UTF-8")));
	}
}
