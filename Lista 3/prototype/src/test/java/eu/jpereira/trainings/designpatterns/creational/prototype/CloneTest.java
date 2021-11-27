package eu.jpereira.trainings.designpatterns.creational.prototype;
import static org.junit.Assert.*;

import eu.jpereira.trainings.designpatterns.creational.prototype.model.Shell;
import eu.jpereira.trainings.designpatterns.creational.prototype.model.Tire;
import eu.jpereira.trainings.designpatterns.creational.prototype.model.Vehicle;
import eu.jpereira.trainings.designpatterns.creational.prototype.model.VehiclePartEnumeration;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CloneTest {

	@Test
	public void testClone() {

		Vehicle vehicle = new Vehicle();
		List<VehiclePart> partList = new ArrayList<VehiclePart>();
		partList.add(new Tire());
		partList.add(new Tire());
		partList.add(new Shell());
		vehicle.setParts(partList);


		assertEquals(vehicle, vehicle.clone());
	}
}
