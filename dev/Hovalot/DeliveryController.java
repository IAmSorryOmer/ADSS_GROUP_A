

import java.util.LinkedList;
import java.util.List;

public class DeliveryController {

	static DeliveryController instance;
	private List<Delivery> deliveries;
	private DestController dControl;
	private DriverController driverControl;
	private TruckController tControl;

	private DeliveryController(){
		deliveries = new LinkedList<>();
		dControl = DestController.getInstance();
		driverControl = DriverController.getInstance();
		tControl = TruckController.getInstance();
	}

	static public DeliveryController getInstance(){
		if (instance == null){
			instance = new DeliveryController();
		}
		return instance;
	}

	public String toString(){
		String ret = "";
		for (Delivery delivery:deliveries){
			ret += "\ndate: " + delivery.getDate();
			ret += "\ntime: " + delivery.getDispatchTime();
			ret += "\nTruck: " + delivery.getTID();
			ret += "\ndriver: " + delivery.getDName();
			ret += "\nsource: " + delivery.getSource();
			ret += "\nweight: " + delivery.getPreWeight();
			int i = 0;
			for (Destination destination:delivery.getDestinationList()){
				ret += "\ndestination" + i + ": " + destination.getAddress();
			}
			ret += "\n";
		}
		return ret;
	}

	public String addDelivery(String date, String time, String ID, String name, String source, int weight, String[] destinations){
		String reply = "";
		if (!checkAreas(destinations)){
			reply += "there are two destinations with different areas\n";
		}
		if (!checkLicense(name, weight)){
			reply += "the driver does not has the license for the truck\n";
		}
		if (!checkMaxWeight(ID, weight)){
			reply += "the truck is loaded beyond the maximum weight it can carry\n";
		}
		for (String destination: destinations){
			if (!checkAddress(destination)){
				reply += "the destination \"" + destination + "\" is not among the available destinations\n";
			}
		}
		return reply;
	}

	private boolean checkAddress(String destination){
		return dControl.checkDestination(destination);
	}

	private boolean checkAreas(String[] destinations){
		return dControl.checkAreas(destinations);
	}

	private boolean checkLicense(String name, int weight){
		switch (driverControl.getLicense(name)){
			case C1New: if (weight > 12){
				return false;
			}
			case C1Old: if (weight >  15){
				return false;
			}
		}
		return true;
	}

	private boolean checkMaxWeight(String ID, int weight){
		return weight <= tControl.getMaxWeight(ID);
	}
}