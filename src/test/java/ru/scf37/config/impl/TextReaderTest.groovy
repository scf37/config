package ru.scf37.config.impl

import java.nio.charset.Charset

import org.junit.Assert

import ru.scf37.config.impl.readers.TextReader;
import spock.lang.Specification

public class TextReaderTest extends Specification {
	def "Text reader returns first found file"() {
	setup:
		TextReader ar = new TextReader([new UrlConfigReader("classpath:test")], Charset.defaultCharset())
	expect:
		ar.read("app", "env", "test.properties")?.startsWith("key2 = 22")
	}
	
	def "Text reader returns null if no file "() {
	setup:
		TextReader ar = new TextReader([new UrlConfigReader("classpath:missingFolder")], Charset.defaultCharset())
	expect:
		Assert.assertNull(ar.read("app", "env", "test.properties"))
	}
}
