package ru.scf37.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify resource from which to load configuration.
 * <p>
 * It can be used in conjuction with {@link ConfigProperty} to load specific property 
 * from specific file or as-is to load text file to String field.
 * 
 * @author asm
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigResource {
	/**
	 * 
	 * @return resource name to load, typically file path relative to configuration root.
	 */
	String value();
}
