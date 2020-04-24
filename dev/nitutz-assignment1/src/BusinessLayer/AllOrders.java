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
	public boolean addOrder(SingleProviderOrder sop) {
		return orders.add(sop);
	}
	
	public boolean removeOrder(SingleProviderOrder sop) {
		return orders.remove(sop);
	}
	
	public SingleProviderOrder getOrdersFromProvider(Provider p){
		for(SingleProviderOrder sop : getOrders())
			if(sop.getProvider().getProviderID().equals(p.getProviderID()))
				return sop;
		return SingleProviderOrder.SingleProviderOrderCreator(p);
	}
}
