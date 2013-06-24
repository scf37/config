package ru.scf37.config.impl;

public final class ConfigUtils {
	private ConfigUtils() {
	}
	
	public static String formatAddress(String application, String version, String environment, String name) {
		return "[app="+application + ", ver=" + version + ", env=" + environment + ", name=" + name + "]";
	}
}
