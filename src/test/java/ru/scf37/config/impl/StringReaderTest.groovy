package ru.scf37.config.impl

import java.nio.charset.Charset

import org.junit.Assert
import org.junit.Test

import ru.scf37.config.ConfigReader
import spock.lang.Specification

public class StringReaderTest extends Specification {
	def "String reader returns first found file"() {
	setup:
		StringReader ar = new StringReader([new UrlConfigReader("classpath:test")], Charset.defaultCharset())
	expect:
		ar.read("app", "v1", "env", "test.properties")?.startsWith("key2 = 22")
	}
	
	def "String reader returns null if no file "() {
	setup:
		StringReader ar = new StringReader([new UrlConfigReader("classpath:missingFolder")], Charset.defaultCharset())
	expect:
		Assert.assertNull(ar.read("app", "v1", "env", "test.properties"))
	}
}
