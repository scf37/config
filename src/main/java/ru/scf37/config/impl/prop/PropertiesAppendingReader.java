package ru.scf37.config.impl.prop;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.scf37.config.ConfigReader;
import ru.scf37.config.impl.ConfigUtils;
/**
 * Reads Properties from given input stream readers and merges them by appending
 * 
 * @author scf37
 *
 */
public class PropertiesAppendingReader implements ConfigReader<Properties> {
	private Logger log = LoggerFactory.getLogger(getClass());
	private List<ConfigReader<InputStream>> readers;
	
	public PropertiesAppendingReader(List<ConfigReader<InputStream>> readers) {
		if (readers == null) {
			throw new IllegalArgumentException("readers cannot be null");
		}
		this.readers = readers;
	}
	
	@Override
	public Properties read(String application, String version, String environment, String name) {
		Properties props = new Properties();
		boolean found = false;
		for (ConfigReader<InputStream> r: readers) {
			found |= append(props, r.read(application, version, environment, name));
			found |= append(props, r.read(application, version, null, name));
			found |= append(props, r.read(application, null, null, name));
			found |= append(props, r.read(null, null, null, name));
		}
		if (found) {
			log.info("Configuration loaded: {}", ConfigUtils.formatAddress(application, version, environment, name));
			return props;
		}
		log.info("Configuration not found: {}", ConfigUtils.formatAddress(application, version, environment, name));
		return null;
	}

	private boolean append(Properties props, InputStream is) {
		if (is == null) {
			return false;
		}
		try {
			Properties newProps = new Properties();
			newProps.load(is);
			
			for (String key: newProps.stringPropertyNames()) {
				if (!props.containsKey(key)) {
					props.put(key, newProps.get(key));
				}
			}
		} catch (IOException e) {
			return false;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		
		return true;
		
	}

}
