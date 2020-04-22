package BusinessLayer;

import java.util.HashMap;
import java.util.Map;


public class SingleProviderOrder {
	
	//fields
	private String OrderID;
	private Provider Provider;
	private Map<CatalogItem, Pair<Integer, Integer>> ItemList_amount_price;
	private int totalItemAmount;
	
	//consructors
	private SingleProviderOrder (Provider provider) {
		this.OrderID = provider.getProviderID();
		this.Provider = provider;
		this.ItemList_amount_price = new HashMap<CatalogItem, Pair<Integer, Integer>>();
		this.totalItemAmount = 0;
	}
	
	//creators
	public static SingleProviderOrder SingleProviderOrderCreator (Provider provider) {
		SingleProviderOrder spo = new SingleProviderOrder(provider);
		return spo;
	}
	
	//methods
	public static boolean AddToOrder (SingleProviderOrder sop, CatalogItem catalogItem, int orderAmount) {
		if (orderAmount < 1)
			return false;
		sop.addToItemList(catalogItem, orderAmount);
		sop.setTotalItemAmount(sop.getTotalItemAmount() + orderAmount);
		return true;
	}
	
	public static boolean EditOrder (SingleProviderOrder sop, CatalogItem catalogItem, int orderAmount) {
		if (orderAmount < 0)
			return false;
		else if (orderAmount == 0)
			return RemoveFromOrder(sop, catalogItem);
		sop.editItemList(catalogItem, orderAmount);
		return true;
	}
	
	public static boolean RemoveFromOrder (SingleProviderOrder sop, CatalogItem catalogItem) {
		sop.removeFromItemList(catalogItem);
		return true;
	}
	
	private static void setprice (SingleProviderOrder sop, CatalogItem catalogItem) {
		//to-do: implement
	}
	
	//getters  & setters
	public Map<CatalogItem, Pair<Integer, Integer>> getItemList() {
		return ItemList_amount_price;
	}

	public void addToItemList(CatalogItem catalogItem, int orderAmount) {
		ItemList_amount_price.put(catalogItem, new Pair(new Integer(orderAmount),new Integer(orderAmount)));
	}
	
	public void editItemList(CatalogItem catalogItem, int orderAmount) {
		ItemList_amount_price.replace(catalogItem, new Pair(new Integer(orderAmount),new Integer(orderAmount)));
	}
	
	public void removeFromItemList(CatalogItem catalogItem) {
		ItemList_amount_price.remove(catalogItem);
	}

	public String getOrderID() {
		return OrderID;
	}

	public Provider getProvider() {
		return Provider;
	}
	
	public int getTotalItemAmount() {
		return totalItemAmount;
	}

	public void setTotalItemAmount(int totalItemAmount) {
		this.totalItemAmount = totalItemAmount;
	}
	
	
}
