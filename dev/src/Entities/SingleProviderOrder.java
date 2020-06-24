package Entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class SingleProviderOrder{
	
	//fields
	private String orderID;
	private int storeId;
	private int driverId;
	private int shift;
	private Provider provider;
	private Map<CatalogItem, Integer> orderItems; //catalog item - amount of item - total price
	private LocalDate orderDate;
	private LocalDate deliveryDate;
	private int orderDays;
	private boolean isShipped;
	private boolean hasArrived;


	//consructors
	public SingleProviderOrder (Provider provider, int storeId, String orderId, LocalDate orderDate, int orderDays) {
		this.orderID = orderId;
		this.storeId = storeId;
		this.provider = provider;
		this.orderItems = new HashMap<CatalogItem, Integer>();
		this.orderDate = orderDate;
		this.orderDays = orderDays;
		this.deliveryDate = null;
		this.hasArrived = false;
		this.isShipped = false;
		this.driverId = 0;
		this.shift = -1;
	}

	public SingleProviderOrder(Provider provider,  int storeId, int driverId, int shift, String orderID, LocalDate orderDate, LocalDate deliveryDate, int orderDays, boolean isShipped, boolean hasArrived) {
		this.orderID = orderID;
		this.storeId = storeId;
		this.driverId = driverId;
		this.shift = shift;
		this.provider = provider;
		this.orderItems = new HashMap<CatalogItem, Integer>();
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.orderDays = orderDays;
		this.isShipped = isShipped;
		this.hasArrived = hasArrived;
	}

	//deep copy of the order items in another order (mostly for automatic orders)
	public SingleProviderOrder(SingleProviderOrder other) {
		this.orderID = other.orderID;
		this.storeId = other.storeId;
		this.driverId = other.driverId;
		this.shift = other.shift;
		this.provider = other.provider;
		this.orderItems = new HashMap<CatalogItem, Integer>(other.getOrderItems());
		this.orderDate = other.orderDate;
		this.deliveryDate = other.deliveryDate;
		this.orderDays = other.orderDays;
		this.isShipped = other.isShipped;
		this.hasArrived = other.hasArrived;
	}

	//constructor for regular order with prepared items
	public SingleProviderOrder(String orderID, int storeId, Provider provider, Map<CatalogItem, Integer> orderItems, LocalDate orderDate) {
		this.orderID = orderID;
		this.storeId = storeId;
		this.driverId = 0;
		this.shift = -1;
		this.provider = provider;
		this.orderItems = orderItems;
		this.orderDate = orderDate;
		this.deliveryDate = null;
		this.orderDays = 0;
		this.hasArrived = false;
		this.isShipped = false;
	}

	//getters  & setters


	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public boolean isHasArrived() {
		return hasArrived;
	}

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

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public int getShift() {
		return shift;
	}

	public void setShift(int shift) {
		this.shift = shift;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
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

	public boolean isShipped() {
		return isShipped;
	}

	public void setShipped(boolean shipped) {
		isShipped = shipped;
	}

	public boolean hasArrived() {
		return hasArrived;
	}

	public void setHasArrived(boolean hasArrived) {
		this.hasArrived = hasArrived;
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
