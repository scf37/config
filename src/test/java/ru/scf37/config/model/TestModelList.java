package ru.scf37.config.model;

import java.util.List;

import ru.scf37.config.annotation.ConfigProperty;

public class TestModelList {
	@ConfigProperty("l1")
	private List<String> v1;
	
	@ConfigProperty("v2")
	private List<Integer> v2;
	
	@ConfigProperty("v3")
	private List<Long> v3;
	
	@ConfigProperty("v4")
	private List<Boolean> v4;
}
