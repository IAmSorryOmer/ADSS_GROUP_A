package BusinessLayer;

import java.util.HashMap;
import java.util.Map;


public class SingleProviderOrder{
	
	//fields
	private final String OrderID;
	private Provider Provider;
	private Map<CatalogItem, Pair<Integer, Double>> ItemList_amount_price; //catalog item - amount of item - total price
	private int totalItemAmount;
	private double finalPrice;
	
	
	//consructors
	public SingleProviderOrder (Provider provider) {
		this.OrderID = provider.getProviderID();
		this.Provider = provider;
		this.ItemList_amount_price = new HashMap<CatalogItem, Pair<Integer, Double>>();
		this.totalItemAmount = 0;
		this.finalPrice = 0;
	}
	
	//getters  & setters
	public void addToItemList(CatalogItem catalogItem, int orderAmount, double totalPrice) {
		ItemList_amount_price.put(catalogItem, new Pair<Integer,Double>(new Integer(orderAmount),new Double(totalPrice)));
	}
	
	public void editItemList(CatalogItem catalogItem, int orderAmount, double totalPrice) {
		ItemList_amount_price.replace(catalogItem, new Pair<Integer, Double>(new Integer(orderAmount),new Double(totalPrice)));
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
	
	public int getSpecificItemAmount (CatalogItem catalogItem) {
		return ItemList_amount_price.get(catalogItem).getFirst();
	}
	
	public double getItemTotalPrice (CatalogItem catalogItem) {
		return ItemList_amount_price.get(catalogItem).getSecond();
	}
	
	public Map<CatalogItem, Pair<Integer, Double>> getItemList_amount_price() {
		return ItemList_amount_price;
	}
	
	public double getFinalPrice() {
		return finalPrice;
	}
	
	public void setFinalPrice(double newFinalPrice) {
		finalPrice = newFinalPrice;
	}
}
