package ru.scf37.config;

/**
 * Exception thrown by Config library in case of any problems.
 * 
 * @author scf37
 *
 */
public class ConfigException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(String message) {
		super(message);
	}
}
