package ru.scf37.config.impl;


public final class ConfigUtils {
	private ConfigUtils() {
	}
	
	public static String formatAddress(String environment, String name) {
		return "[env='" + environment + "', name='" + name + "']";
	}
}
