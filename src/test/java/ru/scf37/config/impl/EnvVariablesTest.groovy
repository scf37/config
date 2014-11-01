package ru.scf37.config.impl

import ru.scf37.config.ConfigFactory
import spock.lang.Specification

public class EnvVariablesTest extends Specification {
	
	def "env variables are readable"() {
		//we can't check overriding since we can't write to env map
	when:
		def reader = ConfigFactory.readPropertiesFrom("classpath:test").overrideWithEnvironmentVariables().build()
		Properties p = reader.read("app", "env", "test.properties")
	then:
		p.Path != null || p.PATH != null 
	}
}
