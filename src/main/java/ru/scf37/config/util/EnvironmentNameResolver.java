package ru.scf37.config.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ru.scf37.config.impl.ConfigLog;
/**
 * Resolves environment name. Algorithm is as follows:
 * <ul>
 * <li>if config.environment property is set, use it </li>
 * <li>else use current host name as environment name</li>
 * </ul>
 *  
 * @author scf37
 *
 */
public class EnvironmentNameResolver {
	private static EnvironmentNameResolver defaultEnvironmentNameResolver = new EnvironmentNameResolver();
	
	public static void setDefaultEnvironmentNameResolver(EnvironmentNameResolver resolver) {
		defaultEnvironmentNameResolver = resolver;
	}
	
	public static EnvironmentNameResolver getDefaultEnvironmentNameResolver() {
		return defaultEnvironmentNameResolver;
	}
	
	public String getEnvironmentName() {
		String environment = System.getProperty("config.environment");
		if (environment != null && !environment.trim().isEmpty()) {
			return environment;
		}
		
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			ConfigLog.warn("EnvironmentNameResolver: Unable to determine host name, using null for environment");
			return null;
		}
	}
}
