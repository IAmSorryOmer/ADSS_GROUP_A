package Entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class SingleProviderOrder{
	
	//fields
	private final String orderID;
	private int storeId;
	private Provider provider;
	private Map<CatalogItem, Integer> orderItems; //catalog item - amount of item - total price
	private LocalDate orderDate;
	private int orderDays;

	
	//consructors
	public SingleProviderOrder (Provider provider, int storeId, String orderId, LocalDate orderDate, int orderDays) {
		this.orderID = orderId;
		this.storeId = storeId;
		this.provider = provider;
		this.orderItems = new HashMap<CatalogItem, Integer>();
		this.orderDate = orderDate;
		this.orderDays = orderDays;
	}

	//constructor for regular order with prepared items
	public SingleProviderOrder(String orderID, int storeId, Provider provider, Map<CatalogItem, Integer> orderItems, LocalDate orderDate) {
		this.orderID = orderID;
		this.storeId = storeId;
		this.provider = provider;
		this.orderItems = orderItems;
		this.orderDate = orderDate;
		this.orderDays = 0;
	}

	//getters  & setters
	public void addToItemList(CatalogItem catalogItem, int orderAmount) {
		orderItems.put(catalogItem, orderAmount);
	}
	
	public void editItemList(CatalogItem catalogItem, int orderAmount) {
		orderItems.replace(catalogItem, orderAmount);
	}
	
	public void removeFromItemList(CatalogItem catalogItem) {
		orderItems.remove(catalogItem);
	}

	public String getOrderID() {
		return orderID;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public Provider getProvider() {
		return provider;
	}

	public int getSpecificItemAmount (CatalogItem catalogItem) {
		return orderItems.get(catalogItem);
	}

	public Map<CatalogItem, Integer> getOrderItems() {
		return orderItems;
	}

	public void setProvider(Entities.Provider provider) {
		this.provider = provider;
	}

	public void setOrderItems(Map<CatalogItem, Integer> orderItems) {
		this.orderItems = orderItems;
	}

	public boolean isItemExist(CatalogItem catalogItem){
		return orderItems.containsKey(catalogItem);
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public void setOrderDays(int orderDays) {
		this.orderDays = orderDays;
	}
	public int getOrderDays(){
		return  this.orderDays;
	}

	public boolean isAutomatic(){
		return  this.orderDays != 0;
	}

	public boolean isComingAtDay(int day){
		return (orderDays & (1 << day)) != 0;
	}
	public String stringifyArrivalDays(){
		String arr[] = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		String result = "";
		for(int i = 0; i< 7;i++){
			if(isComingAtDay(i))
				result += arr[i] + ", ";
		}
		if(result.length() != 0)
			result = result.substring(0,result.length()-2);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		if(isAutomatic()){
			stringBuilder.append("automatic order. come at days: ").append(stringifyArrivalDays())
					.append(", order details: ").append("\n");
		}
		stringBuilder.append("store id: ").append(storeId).append(", Provider name: ").append(provider.getName()).append(", Provider Id: ").append(provider.getProviderID()).
				append(".\nProvider address: ").append(provider.getCommunicationDetails().getAddress())
				.append(".\n").append("Order ID: ").append(orderID).append(", contact phone: ").append(provider.getCommunicationDetails().getPhoneNum()).append(".\n");
		return stringBuilder.toString();
	}
}
