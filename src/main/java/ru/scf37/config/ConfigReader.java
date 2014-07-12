package ru.scf37.config;

import ru.scf37.config.util.EnvironmentNameResolver;

/**
 * Configuration reader. 
 * <p>
 * Reads configuration by provided parameters.
 * 
 * @author scf37
 * @see ConfigFactory
 * @param <T> Type of configuration to read
 */
public interface ConfigReader<T> {
	/**
	 * This method reads configuration according to parameters from 
	 * {base} path defined by ConfigFactory.
	 * <p>
	 * Following paths are tried (in order of decreasing priority):
	 * <ul>
	 * 	<li>{base}/{application}/{environment}/{name}</li>
	 * 	<li>{base}/{application}/{name}</li>
	 *  <li>{base}/{name}</li>
	 * </ul>
	 * Any parameter can be null, in this case it is just omitted from the path.
	 * 
	 * 
	 * @param application application name
	 * @param environment application environment
	 * @param name configuration file name
	 * @return read configuration or null if configuration not found
	 * @throws ConfigException bad url format
	 */
	public T read(String application, String environment, String name);
	
	/**
	 * This method is a shortcut to {@link #read(String, String, String)}.
	 * It assumes null application name and uses default environment, which is determined by
	 * {@link EnvironmentNameResolver#getDefaultEnvironmentNameResolver()}.
	 * 
	 * @param name configuration file name
	 * @return read configuration or null if configuration not found
	 * @throws ConfigException bad url format
	 */
	public T read(String name);
}
