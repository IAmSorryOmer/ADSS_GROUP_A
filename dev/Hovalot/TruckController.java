

import java.util.LinkedList;
import java.util.List;

public class TruckController {
	static TruckController instance;
	private List<Truck> trucks;

	private TruckController(){
		trucks = new LinkedList<>();
	}

    public static TruckController getInstance(){
		if (instance == null){
			instance = new TruckController();
		}
		return instance;
	}
	public void addTruck(String id, int weight, int maxWeight, String model){
		trucks.add(new Truck(id,weight,maxWeight,model));
	}
	public int getMaxWeight(String ID){
		if (getTruck(ID) == null){
			return -1;
		}
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
		if (getTruck(ID) == null){
			return -1;
		}

		return getTruck(ID).getWeight();
	}
}

//Final version