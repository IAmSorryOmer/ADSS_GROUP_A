package com.company.Entities;

import java.util.HashMap;
import java.util.Map;

public class Agreement{
	
	//fields
	private final String ProviderID;
	private Map<CatalogItem, Integer> ItemQuantityForDiscount;	//amount of each item requiered to get discount for it
	private Map<CatalogItem, Double> ItemDiscounts; //discount in % per item
	private boolean updated;
	
	//consructors
	public Agreement(String ProviderID) {
		this.ProviderID = ProviderID;
		this.ItemQuantityForDiscount = new HashMap<CatalogItem, Integer>();
		this.ItemDiscounts = new HashMap<CatalogItem, Double>();
		this.updated = false;
	}
	
	//getters  & setters
	public String getProviderID() {
		return ProviderID;
	}
	
	public void setItemQuantityForDiscount(CatalogItem catalogItem, int quantityForDiscount) {
		if (ItemQuantityForDiscount.containsKey(catalogItem))
			ItemQuantityForDiscount.replace(catalogItem, quantityForDiscount);
	}
	
	public int getItemQuantityForDiscount(CatalogItem catalogItem) {
		if (ItemQuantityForDiscount.containsKey(catalogItem))
			return ItemQuantityForDiscount.get(catalogItem);
		return -1;
	}
	
	public void setItemDiscountPercent(CatalogItem catalogItem, double DiscountPercent) {
		if (ItemDiscounts.containsKey(catalogItem))
			ItemDiscounts.replace(catalogItem, DiscountPercent);
	}
	
	public double getItemDiscountPercent(CatalogItem catalogItem) {
		if (ItemDiscounts.containsKey(catalogItem))
			return ItemDiscounts.get(catalogItem);
		return -1;
	}

	public void addItem(CatalogItem catalogItem, int quantityForDiscount, double discountPercentage) {
		ItemQuantityForDiscount.put(catalogItem, quantityForDiscount);
		ItemDiscounts.put(catalogItem, discountPercentage);
	}
	
	public void removeItemDiscount(CatalogItem catalogItem) {
		setItemDiscountPercent(catalogItem,0);
		setItemQuantityForDiscount(catalogItem, 0);
	}
	
	public boolean removeItem(CatalogItem catalogItem) {
		if (ItemQuantityForDiscount.containsKey(catalogItem) & ItemDiscounts.containsKey(catalogItem)) {
			ItemQuantityForDiscount.remove(catalogItem);
			ItemDiscounts.remove(catalogItem);
			return true;
		}
		return false;
	}

	public boolean doesDiscountExist (CatalogItem catalogItem) {
		return (ItemDiscounts.containsKey(catalogItem) & ItemQuantityForDiscount.containsKey(catalogItem));
	}

	public Map<CatalogItem, Integer> getItemQuantityForDiscount() {
		return ItemQuantityForDiscount;
	}

	public void setItemQuantityForDiscount(Map<CatalogItem, Integer> itemQuantityForDiscount) {
		ItemQuantityForDiscount = itemQuantityForDiscount;
	}

	public Map<CatalogItem, Double> getItemDiscounts() {
		return ItemDiscounts;
	}

	public void setItemDiscounts(Map<CatalogItem, Double> itemDiscounts) {
		ItemDiscounts = itemDiscounts;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
}
