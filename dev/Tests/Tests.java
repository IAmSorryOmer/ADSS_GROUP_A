import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.*;

public class Tests {

    /** Object under test. */


    @Test
    public void driverControllerTest() {
        DriverController.getInstance().addDriver(License.C,"Shimy");
        assertEquals(DriverController.getInstance().getLicense("Shimy"),License.C);

    }

    @Test
    public void driverControllerTestFalse() {
        DriverController.getInstance().addDriver(License.C,"Shimy");
        assertEquals(DriverController.getInstance().getLicense("Barak"),License.NoLicense);

    }
    @Test
    public void truckControllerTestGetWeight() {
        TruckController.getInstance().addTruck("4732",17,85,"Hoverman 2");
        assertEquals(TruckController.getInstance().getWeight("4732"),17);

    }

    @Test
    public void truckControllerTestGetWeightFalse() {
        TruckController.getInstance().addTruck("4732",17,85,"Hoverman 2");
        assertEquals(TruckController.getInstance().getWeight("4730"),-1);

    }

    @Test
    public void truckControllerTestGetMaxWeight() {
        TruckController.getInstance().addTruck("4732",17,85,"Hoverman 2");
        assertEquals(TruckController.getInstance().getMaxWeight("4732"),85);

    }

    @Test
    public void truckControllerTestGetMaxWeightFalse() {
        TruckController.getInstance().addTruck("4732",17,85,"Hoverman 2");
        assertNotSame(TruckController.getInstance().getMaxWeight("4732"),-1);

    }

    @Test
    public void destinationControllerTest() {
        DestController.getInstance().addDestination("BambaLand 18","0381298427", "Omer Barak", "Area C");
        assertEquals(DestController.getInstance().checkDestination("BambaLand 18"),true);
    }

    @Test
    public void destinationControllerTestFalse() {
        DestController.getInstance().addDestination("BambaLand 18","0381298427", "Omer Barak", "Area C");
        assertEquals(DestController.getInstance().checkDestination("42222"),false);
    }

    @Test
    public void destinationControllerTestArea() {
        DestController.getInstance().addDestination("BambaLand 18","0381298427", "Omer Barak", "Area C");
        DestController.getInstance().addDestination("Zura 22","038153435427", "Omer Barad", "Area C");
        DestController.getInstance().addDestination("Yousoro 12","5423435427", "Omer Barach", "Area C");
        List<String> l=new LinkedList<>();
        l.add("BambaLand 18");
        l.add("Zura 22");
        l.add("Yousoro 12");
        assertEquals(DestController.getInstance().checkAreas(l),true);
    }


    @Test
    public void destinationControllerTestAreaFalse() {
        DestController.getInstance().addDestination("BambaLand1 18","0381298427", "Omer Barak", "Area C");
        DestController.getInstance().addDestination("Zura1 22","038153435427", "Omer Barad", "Area B");
        DestController.getInstance().addDestination("Yousoro1 12","5423435427", "Omer Barach", "Area C");
        List<String> l=new LinkedList<>();
        l.add("BambaLand1 18");
        l.add("Zura1 22");
        l.add("Yousoro1 12");
        assertEquals(DestController.getInstance().checkAreas(l),false);
    }

    @Test public void testPop() {
        DestController.getInstance().addDestination("BambaLand 18","0381298427", "Omer Barak", "Area C");
        DestController.getInstance().addDestination("ShlomoHaGever 76","032984322", "Nisim Nisim", "Jerusalem");
        DestController.getInstance().addDestination("Sally Acorn 7","329034223", "Tomer Fridman", "Jerusalem");
        DestController.getInstance().addDestination("Julie-su the echidna 50","0294892001", "Mirai Zura", "Tel Aviv");
        TruckController.getInstance().addTruck("4732",17,85,"Hoverman 2");
        TruckController.getInstance().addTruck("2751",13,62,"Bamboo");
        DriverController.getInstance().addDriver(License.C1New,"Footy Mcsprooge");
        DriverController.getInstance().addDriver(License.CPlusE,"Oldy Feelcheek");
        List<String> nf = new LinkedList<>();
        nf.add("111");
        HashMap<String, String> nd = new HashMap<>();
        nd.put("111", "BambaLand 18");
        HashMap<String, HashMap<String,Integer>> p = new HashMap<>();
        HashMap<String, Integer> p1 = new HashMap<>();
        p1.put("milk", 5);
        p.put("netanya", p1);
        DeliveryController.getInstance().addDelivery("a2", "12", "4732", "Oldy Feelcheek", "a", 3, nf, nd, p);
        assertEquals(DeliveryController.getInstance().toString(),"\ndate: a2" +
                "\ntime: 12" +
                "\nTruck: 4732\n" +
                "driver: Oldy Feelcheek\n" +
                "source: a\n" +
                "weight: 3\n" +
                "destination 1: BambaLand 18\n");
    }


}