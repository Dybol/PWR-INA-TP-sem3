package eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator;

public class WorldCensor extends SocialChannelDecorator {

	@Override
	public void deliverMessage(String message) {
		String[] strings = message.split(" ");
		for(int i = 0; i < strings.length; i++) {
			if(i % 2 == 0)
				strings[i] = "###";
		}
		StringBuilder builder = new StringBuilder();
		for(String s: strings)
			builder.append(s).append(" ");
		builder.deleteCharAt(builder.length() - 1);

		delegate.deliverMessage(builder.toString());
	}
}
