package BL;

import DAL.DTruck;
import DAL.Mapper;

import java.util.LinkedList;
import java.util.List;

public class TruckController {

	private List<Truck> trucks;
	private Object DTruck;

	public TruckController(){
		trucks = new LinkedList<>();
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

	public void Load(int store_num, Mapper mapper) {List<DTruck> dTrucks= mapper.getTrucks();
		for (DTruck x: dTrucks
			 ) {
			trucks.add(new Truck(DTruck));

		}
	}
}

//Final version