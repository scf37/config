package ru.scf37.config.impl.text;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import ru.scf37.config.ConfigReader;
import ru.scf37.config.impl.UrlConfigReader;
/**
 * Builder for String configuration.
 * <p/>
 * String configurations do not support merging, thus first found configuration will be used.
 * 
 * @author scf37
 *
 */
public class TextConfigBuilder {
	private List<ConfigReader<InputStream>> readers = new ArrayList<ConfigReader<InputStream>>();
	
	public TextConfigBuilder() {
	}
	/**
	 * Add new configuration source, of lower priority
	 * 
	 * @param url file:, classpath: or http: url
	 * @return this builder
	 */
	public TextConfigBuilder or(String url) {
		readers.add(makeConfigReader(url));
		return this;
	}
	
	protected ConfigReader<InputStream> makeConfigReader(String url) {
		return new UrlConfigReader(url);
	}
	/**
	 * Builds ConfigReader which can be used to read configuration.
	 * 
	 * @param charset Charset to be used to convert raw data to String. Can be null (system default)
	 * @return ConfigReader
	 */
	public ConfigReader<String> build(Charset charset) {
		return new TextReader(readers, charset);
	}
}
