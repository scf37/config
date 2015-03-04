package ru.scf37.config.impl

import ru.scf37.config.ConfigFactory
import spock.lang.Specification

public class SystemPropertiesTest extends Specification {
	def oldValue
	def setup() {
		oldValue = System.getProperty("key1")
		System.setProperty("key1", "w00t")
	}
	
	def cleanup() {
		if (oldValue == null) {
			System.properties.remove("key1")	
		} else {
			System.setProperty("key1", oldValue)
		}
	}
	
	def "system properties correctly override existing properties"() {
	when:
		System.setProperty("key1", "w00t")
		def reader = ConfigFactory.readPropertiesFrom("classpath:test").overrideWithSystemProperties().build()
		Properties p = reader.read("env", "test.properties")
	then:
		p.key1 == 'w00t'
		p.key2 == '22'
	}
}
