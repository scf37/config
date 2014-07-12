package ru.scf37.config.util;

import static ru.scf37.config.impl.ConfigLog.warn;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import ru.scf37.config.ConfigException;
import ru.scf37.config.ConfigFactory;
import ru.scf37.config.ConfigReader;
import ru.scf37.config.impl.ConfigUtils;

/**
 * Adaptation of Spring's PropertySourcesPlaceholderConfigurer to use with Config library.
 * <p>
 * This class supports all features of Spring and Config library, i.e. 
 * placeholder resolving, properties overriding works as well as properties merging, multiple protocols etc.
 * <p>
 * Usage example:
 * <pre class="code">
 * 	&lt;bean class="ru.scf37.config.util.ConfigPropertyPlaceholderConfigurer"&gt;
		&lt;property name="root" value="classpath:test" /&gt;
		&lt;property name="application" value="app" /&gt;
		&lt;property name="location" value="test.properties" /&gt;
	&lt;/bean&gt;
	</pre>
 * 
 * @author scf37
 *
 */
public class ConfigPropertyPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer implements InitializingBean {
	private List<String> resources = new ArrayList<String>();
	
	private EnvironmentNameResolver environmentNameResolver = EnvironmentNameResolver.getDefaultEnvironmentNameResolver();
	
	private String application;
	private String root = "classpath:";
	/**
	 * Application name
	 * 
	 * @param application application name
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * Configuration root path 
	 * @param root root path URL
	 * @see ConfigFactory#readPropertiesFrom(String)
	 */
	public void setRoot(String root) {
		this.root = root;
	}
	
	/**
	 * Set custom EnvironmentNameResolver
	 * @param environmentNameResolver environment name resolver to use
	 */
	public void setEnvironmentNameResolver(
			EnvironmentNameResolver environmentNameResolver) {
		this.environmentNameResolver = environmentNameResolver;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		List<Resource> resolvedResources = resolveEnvironmentConfig(resources);
		
		super.setLocations(resolvedResources.toArray(new Resource[0]));
	}

	public void setLocations(String[] locations) {
		this.resources = new ArrayList<String>(Arrays.asList(locations));
	}

	public void setLocation(String location) {
		this.resources = new ArrayList<String>(Arrays.asList(new String[] { location }));
	}

	protected List<Resource> resolveEnvironmentConfig(List<String> resources) {
		List<Resource> result = new ArrayList<Resource>();
		for (String res : resources) {
			Resource r = resolve(res);
			if (r != null) {
				result.add(r);
			}
		}
		return result;
	}

	protected Resource resolve(String res) {
		String env = environmentNameResolver.getEnvironmentName();
		String path = res;

		try {
			ConfigReader<Properties> reader = ConfigFactory.readPropertiesFrom(root).build();
			Properties p = reader.read(application, env, path);
			if (p == null) {
				return null;
			}
			StringWriter w = new StringWriter();
			p.store(w, null);
			
			return new ByteArrayResource(w.toString().getBytes(Charset.forName("UTF-8")));
		} catch (ConfigException ex) {
			warn("Unable to resolve environment config for " + ConfigUtils.formatAddress(application, env, path) + ", error=" + ex.getMessage());
		} catch (IOException ex) {
			warn("Unable to resolve environment config for " + ConfigUtils.formatAddress(application, env, path) + ", error=" + ex.getMessage());
		}
		return null;
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
			final ConfigurablePropertyResolver propertyResolver)  {
		
		try {
			logger.info("Following properties were loaded " +  hidePasswords(mergeProperties()));
		} catch (IOException e) {
			warn(e.toString());
		}
		
		super.processProperties(beanFactoryToProcess, propertyResolver);
	}
	
	protected boolean isPasswordKey(String key) {
		if (key == null) return false;
		key = key.toLowerCase();
		return key.contains("password");
	};
	
	private String hidePasswords(Properties p) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String key: p.stringPropertyNames()) {
			if (isPasswordKey(key)) {
				map.put(key, "******");
			} else {
				map.put(key, p.getProperty(key));
			}
		}
		return map.toString();
	}

	
}
