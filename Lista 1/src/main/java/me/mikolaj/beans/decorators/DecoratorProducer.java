package me.mikolaj.beans.decorators;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,
		ElementType.METHOD,
		ElementType.TYPE, 
		ElementType.PARAMETER}) 
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface DecoratorProducer {

	ProducerType type();

	public enum ProducerType { 
		UPPER, LOWER;
	} 
}