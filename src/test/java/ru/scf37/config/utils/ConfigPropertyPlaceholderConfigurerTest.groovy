package ru.scf37.config.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spock.lang.Specification

public class ConfigPropertyPlaceholderConfigurerTest extends Specification {
	
	def "PlaceholderConfigurer correctly loads resources"() {
		when:
			System.getProperties().put('config.environment', 'env')
			ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")
		then:
			notThrown(Exception)
			context.getBean("key1") == '1'
			context.getBean("key2") == '22'
			context.getBean("key3") == '3'
			context.getBean("key4") == '44'
	}
}