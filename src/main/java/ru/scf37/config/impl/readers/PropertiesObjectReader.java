package ru.scf37.config.impl.readers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Properties;

import ru.scf37.config.ConfigException;
import ru.scf37.config.impl.AbstractConfigReader;

public class PropertiesObjectReader extends AbstractConfigReader<InputStream>{
	private Properties properties;
	
	public PropertiesObjectReader(Properties properties) {
		this.properties = properties;
	}
	
	@Override
	public InputStream read(String application, String environment, String name) {
		try {
			StringWriter sw = new StringWriter();
			properties.store(sw, null);
			return new ByteArrayInputStream(sw.toString().getBytes(Charset.forName("UTF-8")));
		} catch (IOException e) {
			throw new ConfigException("Error reading system properties", e);
		}
	}

}
