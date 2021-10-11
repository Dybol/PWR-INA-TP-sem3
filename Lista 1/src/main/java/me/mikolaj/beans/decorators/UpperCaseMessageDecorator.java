package me.mikolaj.beans.decorators;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@DecoratorProducer(type = DecoratorProducer.ProducerType.UPPER)
@Primary
public class UpperCaseMessageDecorator implements MessageDecorator{

	@Override
	public String decorate(String message) {
		return message.toUpperCase(Locale.ROOT);
	}
}
