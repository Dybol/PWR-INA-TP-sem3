package me.mikolaj.beans; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component;

@Component 
public class MessagePrinter { 

	private final MessageProducer producer;

	@Autowired
	public MessagePrinter(final MessageProducer producer) {
		this.producer = producer; 
	}

	public void print() { 
		System.out.println(producer.getMessage()); 
	} 
}