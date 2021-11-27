package eu.jpereira.trainings.designpatterns.structural.proxy;

import eu.jpereira.trainings.designpatterns.structural.proxy.testconfig.TestConfiguration;
import org.junit.Before;

public class ResilientTrafficLightManagerTest extends TrafficLightsManager{

	@Before
	public void setUp(){
		TestConfiguration.fakeFailuresInController = true;
	}

	/**
	 * @param trafficLightFactory
	 */
	public ResilientTrafficLightManagerTest(TrafficLightsFactory trafficLightFactory) {
		super(trafficLightFactory);
	}

	@Override
	protected TrafficLightsManager createTrafficLightManager() {

	}
}
