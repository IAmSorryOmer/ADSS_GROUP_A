package com.company.Entities;

import BL.Store;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class SingleProviderOrder{
	
	//fields
	private final String orderID;
	private final int storeID;
	private Provider provider;
	private Map<CatalogItem, Integer> orderItems; //catalog item - amount of item - total price
	private LocalDate orderDate;

	
	//consructors
	public SingleProviderOrder (Provider provider, String orderId, int storeId, LocalDate orderDate) {
		this.orderID = orderId;
		this.storeID = storeId;
		this.provider = provider;
		this.orderItems = new HashMap<CatalogItem, Integer>();
		this.orderDate = orderDate;
	}

	public SingleProviderOrder(String orderID, int storeId, Provider provider, Map<CatalogItem, Integer> orderItems, LocalDate orderDate) {
		this.orderID = orderID;
		this.provider = provider;
		this.orderItems = orderItems;
		this.orderDate = orderDate;
		this.storeID = storeId;
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

	public int getStoreID() {
		return storeID;
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

	public void setProvider(com.company.Entities.Provider provider) {
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

	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("Provider name: ").append(provider.getName()).append(", Provider Id: ").append(provider.getProviderID()).
				append(".\nProvider address: ").append(provider.getCommunicationDetails().getAddress()).append(", Order Date: ").append(orderDate)
				.append(".\n").append("Order ID: ").append(orderID).append(", contact phone: ").append(provider.getCommunicationDetails().getPhoneNum()).append(".\n");
		return toReturn.toString();
	}
}
