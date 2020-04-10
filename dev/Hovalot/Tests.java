import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public abstract class Tests {

    /** Object under test. */


    @Test
    public void driverControllerTest() {
        DriverController.getInstance().addDriver(new Driver(License.C,"Shimy"));
        assertEquals(DriverController.getInstance().getLicense("Shimy"),License.C);

    }

    @Test
    public void truckControllerTestGetWeight() {
        TruckController.getInstance().addTruck(new Truck("4732",17,85,"Hoverman 2"));
        assertEquals(TruckController.getInstance().getWeight("4732"),17);

    }

    @Test
    public void destinationControllerTest() {
        DestController.getInstance().addDestination(new Destination("BambaLand 18","0381298427", "Omer Barak", "Area C"));
        assertEquals(TruckController.getInstance().getWeight("4732"),17);
    }

    @Test
    public void destinationControllerTest() {
        DeliveryController.getInstance().addDestination(new Destination("BambaLand 18","0381298427", "Omer Barak", "Area C"));
        assertEquals(TruckController.getInstance().getWeight("4732"),17);
    }

    @Test public void testPop() {
        fail("Not yet implemented");
    }


    @Test public void testPush() {
        fail("Not yet implemented");
    }
}