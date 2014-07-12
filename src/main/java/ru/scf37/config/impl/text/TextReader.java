package ru.scf37.config.impl.text;

import static ru.scf37.config.impl.ConfigLog.info;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import ru.scf37.config.ConfigReader;
import ru.scf37.config.impl.AbstractConfigReader;
import ru.scf37.config.impl.ConfigUtils;
/**
 * Reads text from given input stream readers and uses first found one.
 * 
 * @author scf37
 *
 */
public final class TextReader extends AbstractConfigReader<String> {
	private List<ConfigReader<InputStream>> readers;
	private Charset charset;
	
	public TextReader(List<ConfigReader<InputStream>> readers, Charset charset) {
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
	public String read(String application, String environment, String name) {
		for (ConfigReader<InputStream> r: readers) {
			String result = doRead(application, environment, name, r);
			if (result != null) {
				info("Configuration loaded: " + ConfigUtils.formatAddress(application, environment, name));
				return result;
			}
		}
		info("Configuration not found: " + ConfigUtils.formatAddress(application, environment, name));
		return null;
	}

	private String doRead(String application, String environment,
			String name, ConfigReader<InputStream> r) {
		String result;
		
		result = read(r.read(application, environment, name));
		if (result != null) return result;
		
		result = read(r.read(application, null, name));
		if (result != null) return result;
		
		result = read(r.read(application, null, name));
		if (result != null) return result;
		
		result = read(r.read(null, null, name));
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
