package ru.scf37.config.impl.readers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import ru.scf37.config.ConfigException;
import ru.scf37.config.ConfigReader;
import ru.scf37.config.annotation.ConfigProperty;
import ru.scf37.config.annotation.ConfigResource;
import ru.scf37.config.impl.AbstractConfigReader;

class AnnotationReader<T> extends AbstractConfigReader<T> {
	private final Class<T> clazz; 
	private final ConfigReader<Properties> propReader;
	private final ConfigReader<String> textReader;
	
	public AnnotationReader(Class<T> clazz,
			ConfigReader<Properties> propReader, ConfigReader<String> textReader) {
		this.clazz = clazz;
		this.propReader = propReader;
		this.textReader = textReader;
	}



	@Override
	public T read(String application, String environment, String name) {
		try {
			return doRead(application, environment, name);
		} catch (ConfigException e) {
			throw e;
		} catch (Exception e) {
			throw new ConfigException(e.getMessage(), e);
		}
		
	}



	private T doRead(String application, String environment, String name) throws Exception {
		T t = clazz.newInstance();
		Map<String, Object> readerCache = new HashMap<String, Object>();
		
		for (Field f: clazz.getDeclaredFields()) {
			f.setAccessible(true);
			ConfigResource res = f.getAnnotation(ConfigResource.class);
			ConfigProperty prop = f.getAnnotation(ConfigProperty.class);
			
			if (res == null && prop == null) {
				continue;
			}
			
			if (prop == null) {
				f.set(t, get(textReader, application, environment, res.value(), readerCache));
				continue;
			}
			String realName = res == null ? name : res.value();
			
			Properties p = get(propReader, application, environment, realName, readerCache);
			if (p == null) {
				if (prop.mandatory()) {
					throw new ConfigException("Unable to read mandatory property '" 
							+ prop.value() + "': resource '" + realName + "'not found");
				}
				continue;
			}
			
			String value = p.getProperty(prop.value());
			if (value == null) {
				if (prop.mandatory()) {
					throw new ConfigException("Unable to read mandatory property '" 
							+ prop.value() + "': no such property in resource '" + realName + "'");
				}
				continue;
			}
			
			f.set(t, cast(value, f.getType(), prop.value(), realName));		
		}
		
		return t;
	}


	private static final Map<String, Boolean> booleanMap;
	static {
		booleanMap = new HashMap<String, Boolean>();
		booleanMap.put("true", Boolean.TRUE);
		booleanMap.put("false", Boolean.FALSE);
		booleanMap.put("yes", Boolean.TRUE);
		booleanMap.put("no", Boolean.FALSE);
		booleanMap.put("y", Boolean.TRUE);
		booleanMap.put("n", Boolean.FALSE);
	}
	
	private Object cast(String value, Class<?> type, String propName,
			String realName) {
		if (type == String.class) {
			return value;
		}
		if (type == int.class || type == Integer.class) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				throw new ConfigException("Unable to read property '" 
						+ propName + "': can't cast '" + value + "' to Integer (resource '" + realName + "'): " + e.getMessage());
			}
		}
		if (type == long.class || type == Long.class) {
			try {
				return Long.parseLong(value);
			} catch (NumberFormatException e) {
				throw new ConfigException("Unable to read property '" 
						+ propName + "': can't cast '" + value + "' to Long (resource '" + realName + "'): " + e.getMessage());
			}
		}
		if (type == boolean.class || type == Boolean.class) {
			Boolean v = booleanMap.get(value.toLowerCase());
			if (v == null) {
				throw new ConfigException("Unable to read property '" 
						+ propName + "': can't cast '" + value + "' to Boolean (resource '" + realName + "'): supported values are 'true', 'false', 'yes', 'no', 'y', 'n'");
			}
			return v;
		}
		throw new ConfigException("Unsupported field type " + type.getName() + " of property '" + propName + "'");
		
	}



	//we cache reader execution to avoid log spam for multiple invocation
	
	private <T2> T2 get(ConfigReader<T2> reader, String application,
			String environment, String name, Map<String, Object> readerCache) {
		
		String key = reader.getClass() + "#" + application + "#" + environment + "#"
				+ name + "#";
		
				
		@SuppressWarnings("unchecked")
		T2 v = (T2)readerCache.get(key);
		
		if (readerCache.containsKey(key)) {
			return v; //cached value can be null
		}
		
		v = reader.read(application, environment, name);
		readerCache.put(key, v);
		
		return v;
	}



}
