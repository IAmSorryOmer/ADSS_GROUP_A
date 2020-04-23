package BusinessLayer;

import java.util.LinkedList;
import java.util.List;

public class AllOrders {
	
	//fields
	private List<SingleProviderOrder> orders;
		
	
	//consructors
	private static class SingeltonHolder {
		private static AllOrders instance = new AllOrders();
	}
	
	private AllOrders() {
		orders = new LinkedList<SingleProviderOrder>();
	}
	
	//creators
	public static AllOrders getInstance() {
		return SingeltonHolder.instance;
	}
	
	
	//getters  & setters
	public List<SingleProviderOrder> getOrders(){
		return this.orders;
	}
	
	
	//methods
	public static boolean addOrder(AllOrders Orders, SingleProviderOrder sop) {
		return Orders.orders.add(sop);
	}
	
	public static boolean removeOrder(AllOrders Orders, SingleProviderOrder sop) {
		return Orders.orders.remove(sop);
	}
	
	public static SingleProviderOrder getOrdersFromProvider(AllOrders Orders,Provider p){
		for(SingleProviderOrder sop : Orders.getOrders())
			if(sop.getProvider().getProviderID().equals(p.getProviderID()))
				return sop;
		return null;
	}
}