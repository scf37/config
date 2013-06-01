package ru.scf37.config.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ru.scf37.config.ConfigReader;
/**
 * 
 * Builder for Properties configuration.
 * <p/>
 * Properties configurations support merging, thus next configurations 
 * will add properties if they are not already defined.
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
	 * <p/>
	 * Properties from this source will be added to result if they are not alredy defined.
	 * 
	 * @param url file:, classpath: or http: url
	 * @return this builder
	 */
	public PropertiesConfigBuilder append(String url) {
		readers.add(makeConfigReader(url));
		return this;
	}
	
	protected ConfigReader<InputStream> makeConfigReader(String url) {
		return new UrlConfigReader(url);
	}
	/**
	 * Builds ConfigReader which can be used to read Properties configuration. 
	 * 
	 * @return ConfigReader
	 */
	public ConfigReader<Properties> build() {
		return new PropertiesAppendingReader(readers);
	}
}
