package ru.scf37.config.impl

import org.junit.Assert
import org.junit.Test

import ru.scf37.config.ConfigReader
import ru.scf37.config.impl.prop.PropertiesAppendingReader;
import spock.lang.Specification

public class PropertiesAppendingReaderTest extends Specification {
	def "Appending reader mergest properties in correct way"() {
	when:
		PropertiesAppendingReader ar = new PropertiesAppendingReader([new UrlConfigReader("classpath:test")])
		Properties p = ar.read("app", "v1", "env", "test.properties")
	then:
		p as HashMap == [key1:'1', key2:'22', key3:'3', key4:'44']
	}
	
	def "Appending reader returns null if nothing found"() {
	when:
		PropertiesAppendingReader ar = new PropertiesAppendingReader([new UrlConfigReader("classpath:missingFolder")])
	then:
		ar.read("app", "v1", "env", "test.properties") == null
	}
}
