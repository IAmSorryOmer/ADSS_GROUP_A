package BusinessLayer;

import java.util.LinkedList;
import java.util.List;

public class AllOrders {
	
	//fields
	private List<SingleProviderOrder> orders;
	private AllOrders instance;
	
	
	//consructors
	private AllOrders() {
		orders = new LinkedList<SingleProviderOrder>();
	}
	//creators
	public AllOrders getInstance() {
		if(instance == null)
			instance = new AllOrders();
		return instance;
	}
	//getters  & setters
	public List<SingleProviderOrder> getOrders(){
		return this.orders;
	}
	//methods
	public boolean addOrders(AllOrders Orders, SingleProviderOrder sop) {
		return Orders.orders.add(sop);
	}
	public boolean removeOrders(AllOrders Orders, SingleProviderOrder sop) {
		return Orders.orders.remove(sop);
	}//needed?
	
	
	public static List<SingleProviderOrder> getOrdersFromProvider(AllOrders Orders,Provider p){
		List<SingleProviderOrder> ans = new LinkedList<>();
	for(SingleProviderOrder sop : Orders.getOrders()) {
		if(sop.getProvider().equals(p))
			ans.add(sop);
	}
	return ans;
	}
}
