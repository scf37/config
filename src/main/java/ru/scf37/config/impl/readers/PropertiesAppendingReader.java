package ru.scf37.config.impl.readers;

import static ru.scf37.config.impl.ConfigLog.info;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import ru.scf37.config.ConfigReader;
import ru.scf37.config.impl.AbstractConfigReader;
import ru.scf37.config.impl.ConfigUtils;
/**
 * Reads Properties from given input stream readers and merges them by appending.
 * 
 * @author scf37
 *
 */
public final class PropertiesAppendingReader extends AbstractConfigReader<Properties> {
	private List<ConfigReader<InputStream>> readers;
	
	PropertiesAppendingReader(List<ConfigReader<InputStream>> readers) {
		if (readers == null) {
			throw new IllegalArgumentException("readers cannot be null");
		}
		this.readers = readers;
	}
	
	@Override
	public Properties read(String environment, String name) {
		if (name == null) {
			throw new NullPointerException("name cannot be null");
		}
		Properties props = new Properties();
		boolean found = false;
		for (ConfigReader<InputStream> r: readers) {
			found |= append(props, r.read(null, name));
			if (environment != null) {
				found |= append(props, r.read(environment, name));
			}
		}
		if (found) {
			info("Configuration loaded: " + ConfigUtils.formatAddress(environment, name));
			return props;
		}
		info("Configuration not found: " + ConfigUtils.formatAddress(environment, name));
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
				props.put(key, newProps.get(key));
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
