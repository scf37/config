package ru.scf37.config.impl

import ru.scf37.config.impl.prop.PropertiesAppendingReader
import spock.lang.Specification

public class PropertiesAppendingReaderTest extends Specification {
	def "Appending reader merges properties in correct way"() {
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
	
	def "Appending reader merges properties correctly when using multiple sources"() {
	when:
		PropertiesAppendingReader ar = new PropertiesAppendingReader(
			[new UrlConfigReader("classpath:test"), new UrlConfigReader("classpath:test2")])
		Properties p = ar.read("app", "v1", "env", "test.properties")
	then:
		p as HashMap == [key1:'1_2', key2:'2_22', key3:'3', key4:'44']
	}
}
