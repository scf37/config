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
	 * 	<li>{base}/{environment}/{name}</li>
	 *  <li>{base}/{name}</li>
	 * </ul>
	 * Environment can be null, in this case it is just omitted from the path.
	 * 
	 * @param environment application environment
	 * @param name configuration file name
	 * @return read configuration or null if configuration not found
	 * @throws ConfigException bad url format
	 */
	public T read(String environment, String name);
	
	/**
	 * This method is a shortcut to {@link #read(String, String)}.
	 * It uses default environment, which is determined by
	 * {@link EnvironmentNameResolver#getDefaultEnvironmentNameResolver()}.
	 * 
	 * @param name configuration file name
	 * @return read configuration or null if configuration not found
	 * @throws ConfigException bad url format
	 */
	public T read(String name);
}
