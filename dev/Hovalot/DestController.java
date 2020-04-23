

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DestController {
	static DestController instance;
	private List<Destination> destinations;

	private DestController(){
		destinations = new LinkedList<>();
	}

	public static DestController getInstance(){
		if (instance == null){
			instance = new DestController();
		}
		return instance;
	}

	public boolean checkDestination(String address){
		return getDest(address) != null;
	}
	public void addDestination(String address, String contact, String phone, String area){
		destinations.add(new Destination(address,contact,phone,area));
	}
	public boolean checkAreas(Collection<String> addresses){
		String area = getDest(addresses.iterator().next()).getArea();
		for (String address: addresses){
			if (!getDest(address).getArea().equals(area)){
				return false;
			}
		}
		return true;
	}

	public Destination getDest(String address){
		for (Destination destination:destinations){
			if (destination.getAddress().equals(address)){
				return destination;
			}
		}
		return  null;
	}
}
//Final version