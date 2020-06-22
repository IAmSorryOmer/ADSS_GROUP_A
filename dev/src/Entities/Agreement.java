package Entities;

import java.util.HashMap;
import java.util.Map;

public class Agreement{
	
	//fields
	private final String ProviderID;
	private Map<CatalogItem, Pair<Integer, Double>> agreementItems;	//amount of each item requiered to get discount for it
	private boolean updated;
	
	//consructors
	public Agreement(String ProviderID) {
		this.ProviderID = ProviderID;
		this.agreementItems = new HashMap<>();
		this.updated = false;
	}
	
	//getters  & setters
	public String getProviderID() {
		return ProviderID;
	}
	
	public void setItemQuantityForDiscount(CatalogItem catalogItem, int quantityForDiscount) {
		if (agreementItems.containsKey(catalogItem))
			agreementItems.get(catalogItem).setFirst(quantityForDiscount);
	}
	
	public int getItemQuantityForDiscount(CatalogItem catalogItem) {
		if (agreementItems.containsKey(catalogItem))
			return agreementItems.get(catalogItem).getFirst();
		return -1;
	}
	
	public void setItemDiscountPercent(CatalogItem catalogItem, double DiscountPercent) {
		if (agreementItems.containsKey(catalogItem))
			agreementItems.get(catalogItem).setSecond(DiscountPercent);
	}
	
	public double getItemDiscountPercent(CatalogItem catalogItem) {
		if (agreementItems.containsKey(catalogItem))
			return agreementItems.get(catalogItem).getSecond();
		return -1;
	}

	public void addItem(CatalogItem catalogItem, int quantityForDiscount, double discountPercentage) {
		agreementItems.put(catalogItem, new Pair<>(quantityForDiscount, discountPercentage));
	}

	public void removeItem(CatalogItem catalogItem) {
		if (agreementItems.containsKey(catalogItem)) {
			agreementItems.remove(catalogItem);
		}
	}

	public boolean doesDiscountExist (CatalogItem catalogItem) {
		return agreementItems.containsKey(catalogItem);
	}

	public Map<CatalogItem, Pair<Integer,Double>> getAgreementItems() {
		return agreementItems;
	}

	public void setAgreementItems(Map<CatalogItem, Pair<Integer, Double>> agreementItems) {
		this.agreementItems = agreementItems;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
}
