package ru.scf37.config.impl;

public class ConfigLog {
	public static void info(String s) {
		System.out.println(s);
	}
	
	public static void warn(String s) {
		System.err.println(s);
	}
}
