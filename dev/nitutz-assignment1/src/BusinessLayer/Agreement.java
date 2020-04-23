package BusinessLayer;

import java.util.HashMap;
import java.util.Map;

public class Agreement {
	
	//fields
	private int QuantityForDiscount;
	private Map<CatalogItem, Integer> LargeQuantityDiscount; //discount in % per item
	
	//consructors
	private Agreement(int quantityForDiscount) {
		this.QuantityForDiscount = quantityForDiscount;
		this.LargeQuantityDiscount = new HashMap<CatalogItem, Integer>();
	}
	
	//creators
	public static Agreement AgreementCreator(int quantityForDiscount) {
		Agreement agreement = new Agreement(quantityForDiscount);
		return agreement;
	}

	//getters  & setters
	public int getQuantityForDiscount() {
		return QuantityForDiscount;
	}

	public void setQuantityForDiscount(int quantityForDiscount) {
		QuantityForDiscount = quantityForDiscount;
	}
	
	public boolean doesDiscountExist (CatalogItem catalogItem) {
		return LargeQuantityDiscount.containsKey(catalogItem);
	}
	
	public void addToLargeQuantityDiscount(CatalogItem catalogItem, int discount) {
		LargeQuantityDiscount.put(catalogItem, new Integer(discount));
	}
	
	public void editLargeQuantityDiscount(CatalogItem catalogItem, int discount) {
		LargeQuantityDiscount.replace(catalogItem, new Integer(discount));
	}
	
	public void removeFromLargeQuantityDiscount(CatalogItem catalogItem) {
		LargeQuantityDiscount.remove(catalogItem);
	}
	
	//methods
	public static boolean addItemDiscount (Agreement agreement, CatalogItem catalogItem, int discount) {
		if ((discount < 0) | !agreement.doesDiscountExist(catalogItem))
			return false;
		agreement.addToLargeQuantityDiscount(catalogItem, discount);
		return true;
	}
	
	public static boolean editItemDiscount (Agreement agreement, CatalogItem catalogItem, int discount) {
		if (discount < 0)
			return false;
		else if (!agreement.doesDiscountExist(catalogItem))
			return Agreement.addItemDiscount(agreement, catalogItem, discount);
		agreement.editLargeQuantityDiscount(catalogItem, discount);
		return true;
	}
	
	public static boolean removeItemDiscount(Agreement agreement, CatalogItem catalogItem) {
		if (!agreement.doesDiscountExist(catalogItem))
			return false;
		agreement.removeFromLargeQuantityDiscount(catalogItem);
		return true;
	}
}
