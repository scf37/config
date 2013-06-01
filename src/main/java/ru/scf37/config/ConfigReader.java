package ru.scf37.config;

/**
 * Configuration reader. 
 * <p/>
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
	 * <p/>
	 * Following paths are tried (in order of decreasing priority):
	 * <ul>
	 * 	<li>{base}/{application}/{version}/{environment}/{name}</li>
	 * 	<li>{base}/{application}/{version}/{name}</li>
	 *  <li>{base}/{application}/{name}</li>
	 *  <li>{base}/{name}</li>
	 * </ul>
	 * Any parameter can be null, in this case it is just omitted from the path.
	 * </p>
	 * 
	 * @param application application name
	 * @param version application version
	 * @param environment application environment
	 * @return read configuration or null if configuration not found
	 * @throws ConfigException bad url format
	 */
	public T read(String application, String version, String environment, String name);
}
