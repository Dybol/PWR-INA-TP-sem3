package me.mikolaj.beans.decorators;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@DecoratorProducer(type = DecoratorProducer.ProducerType.LOWER)
public class LowerCaseMessageDecorator implements MessageDecorator{

	@Override
	public String decorate(String message) {
		return message.toLowerCase(Locale.ROOT);
	}
}
