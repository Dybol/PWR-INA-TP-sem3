package me.mikolaj.beans; 
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component 
public class SimpleMessageProducer implements MessageProducer {

	@Override 
	public String getMessage() {
		return "Hello World:) Today is: " + LocalDateTime.now();
	} 
}
