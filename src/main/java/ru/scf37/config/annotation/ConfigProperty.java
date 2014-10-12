package ru.scf37.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * This annotation is used to load specific properties to fields. 
 * <p>
 * It supports following types: String, int, Integer, long, Long.
 * 
 * @author asm
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigProperty {
	String value();
	boolean mandatory() default true;
}
