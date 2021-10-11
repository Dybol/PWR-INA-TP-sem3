package me.mikolaj.beans; 
import org.springframework.stereotype.Component; 

@Component 
public class SimpleMessageProducer implements MessageProducer {

	@Override 
	public String getMessage() {
		return "Hello World! Current time in ms: " + System.currentTimeMillis();
	} 
}
