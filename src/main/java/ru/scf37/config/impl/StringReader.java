package ru.scf37.config.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.scf37.config.ConfigReader;
/**
 * Reads Properties from given input stream readers and merges them by appending
 * 
 * @author scf37
 *
 */
public class StringReader implements ConfigReader<String> {
	private Logger log = LoggerFactory.getLogger(getClass());
	private List<ConfigReader<InputStream>> readers;
	private Charset charset;
	
	public StringReader(List<ConfigReader<InputStream>> readers, Charset charset) {
		if (readers == null) {
			throw new IllegalArgumentException("readers cannot be null");
		}
		if (charset == null) {
			charset = Charset.defaultCharset();
		}
		this.readers = readers;
		this.charset = charset;
	}
	
	@Override
	public String read(String application, String version, String environment, String name) {
		for (ConfigReader<InputStream> r: readers) {
			String result = doRead(application, version, environment, name, r);
			if (result != null) {
				log.info("Configuration loaded: {}", ConfigUtils.formatAddress(application, version, environment, name));
				return result;
			}
		}
		log.info("Configuration not found: {}", ConfigUtils.formatAddress(application, version, environment, name));
		return null;
	}

	private String doRead(String application, String version, String environment,
			String name, ConfigReader<InputStream> r) {
		String result;
		
		result = read(r.read(application, version, environment, name));
		if (result != null) return result;
		
		result = read(r.read(application, version, null, name));
		if (result != null) return result;
		
		result = read(r.read(application, null, null, name));
		if (result != null) return result;
		
		result = read(r.read(null, null, null, name));
		return result;
	}

	private String read(InputStream is) {
		if (is == null) {
			return null;
		}
		try {
			Reader r = new InputStreamReader(is, charset);
			char buf[] = new char[1024];
			StringBuilder result = new StringBuilder();
			
			for (;;) {
				int len = r.read(buf);
				if (len < 1) break;
				result.append(buf, 0, len);
			}
			return result.toString();
		} catch (IOException e) {
			return null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		
	}

}
