package ru.scf37.config.model;

import ru.scf37.config.annotation.ConfigProperty;
import ru.scf37.config.annotation.ConfigResource;

public class TestModel {
	@ConfigProperty("v1")
	private String v1;
	
	@ConfigProperty("v2")
	private int v2;
	
	@ConfigProperty("v3")
	private Integer v3;
	
	@ConfigProperty("v4")
	private long v4;
	
	@ConfigProperty(value="v5", mandatory = false)
	private Long v5 = 42L;
	
	@ConfigProperty(value="v6")
	private boolean v6;
	
	@ConfigProperty(value="v7")
	private Boolean v7;
	
	@ConfigResource("text.txt")
	private String text;
	
	@ConfigResource("test2.properties")
	@ConfigProperty("some.key")
	private String value;
}
