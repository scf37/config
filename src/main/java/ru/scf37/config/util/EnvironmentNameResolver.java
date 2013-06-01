package ru.scf37.config.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
	
	public String getEnvironmentName() {
		String environment = System.getProperty("config.environment");
		if (environment != null && !environment.trim().isEmpty()) {
			return environment;
		}
		
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return null;
		}
	}
}
