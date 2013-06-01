package ru.scf37.config.impl

import org.junit.Assert
import org.junit.Test

import ru.scf37.config.ConfigFactory;
import ru.scf37.config.ConfigReader
import ru.scf37.config.impl.prop.PropertiesAppendingReader;
import spock.lang.Specification

public class SystemPropertiesTest extends Specification {
	def "Appending system properties works"() {
	when:
		def reader = ConfigFactory.readSystemProperties().build();
		Properties p = reader.read("app", "v1", "env", "test.properties")
	then:
		p.containsKey("java.runtime.name")
	}
}
