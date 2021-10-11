package me.mikolaj.beans; 
import me.mikolaj.beans.decorators.MessageDecorator;
import me.mikolaj.beans.decorators.DecoratorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component 
public class MessagePrinter { 

	private final MessageProducer producer;
	private final MessageDecorator decorator;

	@Autowired
	public MessagePrinter(final MessageProducer producer,
						  @DecoratorProducer(type = DecoratorProducer.ProducerType.UPPER) final MessageDecorator decorator) {
		this.producer = producer;
		this.decorator = decorator;
	}

	public void print() {
		System.out.println(decorator.decorate(producer.getMessage()));
	}

}