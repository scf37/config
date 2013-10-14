package ru.scf37.config.util;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static ru.scf37.config.impl.ConfigLog.*;

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
 * <p/>
 * This class supports all features of Spring and Config library, i.e. 
 * placeholder resolving, properties overriding works as well as properties merging, multiple protocols etc.
 * <p/>
 * Usage example:
 * <pre class="code">
 * 	&lt;bean class="ru.scf37.config.util.ConfigPropertyPlaceholderConfigurer">
		&lt;property name="root" value="classpath:test" />
		&lt;property name="application" value="app" />
		&lt;property name="version" value="v1" />
		&lt;property name="location" value="test.properties" />
	&lt;/bean>
	</pre>
 * 
 * @author scf37
 *
 */
public class ConfigPropertyPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer implements InitializingBean {
	private List<String> resources = new ArrayList<String>();
	
	private EnvironmentNameResolver environmentNameResolver = new EnvironmentNameResolver();
	
	private String application;
	private String version;
	private String root = "classpath:";
	/**
	 * Application name
	 * 
	 * @param application
	 */
	public void setApplication(String application) {
		this.application = application;
	}
	/**
	 * Application version
	 * 
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * Configuration root path 
	 * @param root
	 */
	public void setRoot(String root) {
		this.root = root;
	}
	
	/**
	 * Set custom EnvironmentNameResolver
	 * @param environmentNameResolver
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
			Properties p = reader.read(application, version, env, path);
			if (p == null) {
				return null;
			}
			StringWriter w = new StringWriter();
			p.store(w, null);
			
			return new ByteArrayResource(w.toString().getBytes(Charset.forName("UTF-8")));
		} catch (ConfigException ex) {
			warn("Unable to resolve environment config for " + ConfigUtils.formatAddress(application, version, env, path) + ", error=" + ex.getMessage());
		} catch (IOException ex) {
			warn("Unable to resolve environment config for " + ConfigUtils.formatAddress(application, version, env, path) + ", error=" + ex.getMessage());
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
	private String hidePasswords(Properties p) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String key: p.stringPropertyNames()) {
			if (key.toLowerCase().contains("password")) {
				map.put(key, "******");
			} else {
				map.put(key, p.getProperty(key));
			}
		}
		return map.toString();
	};
}
