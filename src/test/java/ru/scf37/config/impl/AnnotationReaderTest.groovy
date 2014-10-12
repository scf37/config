package ru.scf37.config.impl

import ru.scf37.config.ConfigException;
import ru.scf37.config.ConfigFactory;
import ru.scf37.config.impl.readers.PropertiesAppendingReader
import ru.scf37.config.model.TestModel;
import spock.lang.Specification

public class AnnotationReaderTest extends Specification {
	def "Reading all values"() {
	when:
		def r = readClass("test.properties")
	then:
		r.v1 == '1'
		r.v2 == 2
		r.v3 == 3
		r.v4 == 4L
		r.v5 == 5L
		r.text == 'Hello, World!'
		r.value == 'value'
	}
	
	def "Reading all values, using default for non-mandatory"() {
	when:
		def r = readClass("test-missing.properties")
	then:
		r.v1 == '1'
		r.v2 == 2
		r.v3 == 3
		r.v4 == 4L
		r.v5 == 42L
		r.text == 'Hello, World!'
		r.value == 'value'
	}
	
	def "Exception in case of missing mandatory value"() {
	when:
		def r = readClass("test-missing-mandatory.properties")
	then:
		def e = thrown(ConfigException)
		e.message.contains "'v4'"
	}
	
	def "Exception in case of uncastable bad property type"() {
		when:
			def r = readClass("test-bad-type.properties")
		then:
			def e = thrown(ConfigException)
			e.message.contains "'v5'"
		}

	
	private TestModel readClass(def name) {
		ConfigFactory.readPropertiesFrom("classpath:/test3")
			.buildAnnotationReader(TestModel).read(name)
	}
}
