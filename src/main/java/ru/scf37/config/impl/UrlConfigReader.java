package ru.scf37.config.impl;

import static ru.scf37.config.impl.ConfigLog.info;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import ru.scf37.config.ConfigException;
/**
 * Loads data from specified URL
 * <p>
 * supports:<ul>
 * <li> classpath urls i.e. classpath:/a/b/c
 * <li> java.net.URLs including file: and http:
 * </ul>
 * @author scf37
 *
 */
public class UrlConfigReader extends AbstractConfigReader<InputStream> {
	private String url;
	
	public UrlConfigReader(String url) {
		if (url == null) {
			throw new IllegalArgumentException("url cannot be null");
		}
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		if (url.indexOf(':') < 0 || 
				url.indexOf(':') == 1) { //windows absolute path
			url = "file:" + url;
		}
		url = url.replaceAll("\\\\", "/");
		this.url = url;
	}
	
	@Override
	public InputStream read(String environment, String name) {
		if (name == null) {
			throw new NullPointerException("name cannot be null");
		}
		String url = appendParameters(environment, name);
		
		return readUrl(url);		
	}

	private String appendParameters(String environment, String name) {
		String url = this.url;
		if (environment != null) {
			url += "/" + environment;
		}
		url += "/" + name;
		return url;
	}

	private InputStream readUrl(String url) {
		if (url.startsWith("classpath:")) {
			return classpathUrl(url.substring("classpath:".length()));
		} else {
			try {
				return url(new URL(url));
			}
			catch (MalformedURLException ex) {
				throw new ConfigException("Invalid url format: '" + url + "'", ex);
			}
		}
	}
	/**
	 * Opens URL.
	 * 
	 * @param u url to open
	 * @return input stream or null
	 */
	protected InputStream url(URL u) {
		URLConnection connection = null;
		try {
			connection = u.openConnection();
			connection.setUseCaches(connection.getClass().getName().startsWith("JNLP"));
			InputStream is = connection.getInputStream();
			info("Reading " + u);
			return is;
		}
		catch (IOException ex) {
			if (connection instanceof HttpURLConnection) {
				((HttpURLConnection) connection).disconnect();
			}
			return null;
		}
	}
	/**
	 * Opens location as classpath location.
	 * 
	 * @param location location, already strept from classpath: prefix
	 * @return InputStream or null
	 */
	protected InputStream classpathUrl(String location) {
		if (location.startsWith("/")) {
			location = location.substring(1);
		}
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
		if (is != null) {
			info("Reading classpath:" + location);
		}
		return is;
	}
	
}
