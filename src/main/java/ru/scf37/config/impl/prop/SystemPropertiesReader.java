package ru.scf37.config.impl.prop;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import ru.scf37.config.ConfigException;
import ru.scf37.config.ConfigReader;

public class SystemPropertiesReader implements ConfigReader<InputStream>{

	@Override
	public InputStream read(String application, String environment, String name) {
		try {
			StringWriter sw = new StringWriter();
			System.getProperties().store(sw, null);
			return new ByteArrayInputStream(sw.toString().getBytes(Charset.forName("UTF-8")));
		} catch (IOException e) {
			throw new ConfigException("Error reading system properties", e);
		}
	}

}
