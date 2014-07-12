package ru.scf37.config.impl;

import ru.scf37.config.ConfigReader;
import ru.scf37.config.util.EnvironmentNameResolver;

public abstract class AbstractConfigReader<T> implements ConfigReader<T>{

	@Override
	public T read(String name) {
		return read(null, 
				EnvironmentNameResolver.getDefaultEnvironmentNameResolver().getEnvironmentName(), 
				name);
	}

}
