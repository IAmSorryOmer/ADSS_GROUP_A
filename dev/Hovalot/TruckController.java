

import java.util.LinkedList;
import java.util.List;

public class TruckController {
	static TruckController instance;
	private List<Truck> trucks;

	private TruckController(){
		trucks = new LinkedList<>();
	}

	static public TruckController getInstance(){
		if (instance == null){
			instance = new TruckController();
		}
		return instance;
	}

	public int getMaxWeight(String ID){
		return getTruck(ID).getMaxWeight();
	}

	private Truck getTruck(String ID){
		for (Truck truck:trucks){
			if (truck.getId().equals(ID)){
				return truck;
			}
		}
		return null;
	}

	public int getWeight(String ID){
		return getTruck(ID).getWeight();
	}
}
