package ru.scf37.config.impl

import ru.scf37.config.ConfigFactory
import spock.lang.Specification

public class SystemPropertiesTest extends Specification {
	def "Appending system properties works"() {
	when:
		def reader = ConfigFactory.readSystemProperties().build();
		Properties p = reader.read("app", "env", "test.properties")
	then:
		p.containsKey("java.runtime.name")
	}
}
