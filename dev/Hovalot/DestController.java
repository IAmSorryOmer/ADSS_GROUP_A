

import java.util.LinkedList;
import java.util.List;

public class DestController {
	static DestController instance;
	private List<Destination> destinations;

	private DestController(){
		destinations = new LinkedList<>();
	}

	static public DestController getInstance(){
		if (instance == null){
			instance = new DestController();
		}
		return instance;
	}

	public boolean checkDestination(String address){
		return getDest(address) != null;
	}

	public boolean checkAreas(String[] addresses){
		String area = getDest(addresses[0]).getArea();
		for (String address: addresses){
			if (!getDest(address).getArea().equals(area)){
				return false;
			}
		}
		return true;
	}

	private Destination getDest(String address){
		for (Destination destination:destinations){
			if (destination.getAddress().equals(address)){
				return destination;
			}
		}
		return  null;
	}
}
