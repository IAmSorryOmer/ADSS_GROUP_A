package BusinessLayer;

import java.util.HashMap;
import java.util.Map;

public class Agreement {
	
	//fields
	private final String ProviderID;
	private Map<CatalogItem, Integer> ItemQuantityForDiscount;	//amount of each item requiered to get discount for it
	private Map<CatalogItem, Integer> ItemDiscounts; //discount in % per item
	
	//consructors
	public Agreement(String ProviderID) {
		this.ProviderID = ProviderID;
		this.ItemQuantityForDiscount = new HashMap<CatalogItem, Integer>();
		this.ItemDiscounts = new HashMap<CatalogItem, Integer>();
	}
	
	//getters  & setters
	public String getProviderID() {
		return ProviderID;
	}
	
	public void setItemQuantityForDiscount(CatalogItem catalogItem, int quantityForDiscount) {
		if (ItemQuantityForDiscount.containsKey(catalogItem))
			ItemQuantityForDiscount.replace(catalogItem, new Integer(quantityForDiscount));
	}
	
	public int getItemQuantityForDiscount(CatalogItem catalogItem) {
		if (ItemQuantityForDiscount.containsKey(catalogItem))
			return ItemQuantityForDiscount.get(catalogItem);
		return -1;
	}
	
	public void setItemDiscountPercent(CatalogItem catalogItem, int DiscountPercent) {
		if (ItemDiscounts.containsKey(catalogItem))
			ItemDiscounts.replace(catalogItem, new Integer(DiscountPercent));
	}
	
	public int getItemDiscountPercent(CatalogItem catalogItem) {
		if (ItemDiscounts.containsKey(catalogItem))
			return ItemDiscounts.get(catalogItem);
		return -1;
	}
	
	public void addItem(CatalogItem catalogItem) {
		ItemQuantityForDiscount.put(catalogItem, new Integer(0));
		ItemDiscounts.put(catalogItem, new Integer(0));
	}
	
	public void addItem(CatalogItem catalogItem, int quantityForDiscount, int discountPercentage) {
		ItemQuantityForDiscount.put(catalogItem, new Integer(quantityForDiscount));
		ItemDiscounts.put(catalogItem, new Integer(discountPercentage));
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
	
}
