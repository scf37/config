package ru.scf37.config.impl

import ru.scf37.config.ConfigReader
import spock.lang.Specification

public class UrlConfigReaderTest extends Specification {
	
	def "reading from classpath works"() {
	when:
		ConfigReader<InputStream> r = new UrlConfigReader("classpath:test")
	then:
		exists(r.read("env", "test.properties"))

	when:		
		r = new UrlConfigReader("classpath:/test/")
	then:
		exists(r.read(null, "test.properties"))
		exists(r.read("env", "test.properties"))
		exists(r.read("env", "/test.properties"))
		exists(r.read("env", "conf/test2.properties"))
		exists(r.read("env", "/conf/test2.properties"))
		r.read("env", "/conf/missing.properties") == null
	}
	
	def "reading filesystem works"() {
	when:
		ConfigReader<InputStream> r = new UrlConfigReader("file:src/test/resources/test")
	then:
		exists(r.read("env", "test.properties"))
	when:	
		r = new UrlConfigReader("file:src/test/resources/test")
	then:
		exists(r.read("env", "test.properties"))
		exists(r.read("env", "/test.properties"))
		exists(r.read("env", "conf/test2.properties"))
		exists(r.read("env", "/conf/test2.properties"))
		r.read("env", "/conf/missing.properties") == null
	when: 'absolute path, with protocol'
		r = new UrlConfigReader('file:' + System.getProperty("user.home"))
	then:
		r.read("env", "conf/missing.properties") == null
	when: 'absolute path, w/o protocol'
		r = new UrlConfigReader(System.getProperty("user.home"))
	then:
		r.read("env", "conf/missing.properties") == null
	}
	
	def "reading via http works"() {
	when:
		ConfigReader<InputStream> r = new UrlConfigReader("http://google.com")
	then:
		exists(r.read(null, "robots.txt"))
		r.read(null, "missingFile.txt") == null
	}

	private boolean exists(InputStream is) {
		try {
			is?.read()
			is?.close()
			return is != null
		} catch (IOException e) {
			throw new RuntimeException(e)
		}
	}
}
