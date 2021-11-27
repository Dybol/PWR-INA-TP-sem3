package eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator;

import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannel;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelBuilder;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelProperties;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelPropertyKey;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.spy.TestSpySocialChannel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorldCensorTest extends AbstractSocialChanneldDecoratorTest {

	@Test
	public void testTruncate() {
		// Create the builder
		SocialChannelBuilder builder = createTestSpySocialChannelBuilder();

		// create a spy social channel
		SocialChannelProperties props = new SocialChannelProperties().putProperty(SocialChannelPropertyKey.NAME, TestSpySocialChannel.NAME);
		SocialChannel channel = builder.with(new WorldCensor()).getDecoratedChannel(props);

		// Now call channel.deliverMessage
		channel.deliverMessage("test test test");

		// Spy channel. Should get the one created before
		TestSpySocialChannel spyChannel = (TestSpySocialChannel) builder.buildChannel(props);
		assertEquals("### test ###", spyChannel.lastMessagePublished());
	}
}
