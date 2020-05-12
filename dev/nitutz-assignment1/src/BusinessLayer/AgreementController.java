package BusinessLayer;

import java.util.List;

public class AgreementController {

	public static List<Agreement> agreementList;
	
	//creators
	public static Agreement AgreementCreator() {
		Agreement agreement = new Agreement();
		agreementList.add(agreement);
		return agreement;
	}
		
	//mothods
	public static boolean addItem (Agreement agreement, CatalogItem catalogItem) {
		if (!agreement.doesDiscountExist(catalogItem))
			return false;
		agreement.addItem(catalogItem);
		return true;
	}
	
	public static boolean addItemDiscount (Agreement agreement, CatalogItem catalogItem, int quantity, int discount) {
		if ((discount < 0) | (quantity < 0) | !agreement.doesDiscountExist(catalogItem))
			return false;
		if ((discount == 0 ) & (quantity != 0))
			return false;
		if ((discount != 0 ) & (quantity == 0))
			return false;
		if ((discount != 0 ) & (quantity == 0))
			return AgreementController.addItem(agreement, catalogItem);
		agreement.addItem(catalogItem, quantity, discount);
		return true;
	}
	
	public static boolean editItem (Agreement agreement, CatalogItem catalogItem, int quantity, int discount) {
		if (discount < 0 | !agreement.doesDiscountExist(catalogItem))
			return false;
		if (quantity <= 0 | !agreement.doesDiscountExist(catalogItem))
			return false;
		agreement.setItemDiscountPercent(catalogItem, discount);
		return true;
	}
	
	/*
	public static boolean editItemDiscountPercent (Agreement agreement, CatalogItem catalogItem, int discount) {
		if (discount < 0 | !agreement.doesDiscountExist(catalogItem))
			return false;
		agreement.setItemDiscountPercent(catalogItem, discount);
		return true;
	}
	
	public static boolean editItemQuantityForDiscount (Agreement agreement, CatalogItem catalogItem, int quantity) {
		if (quantity <= 0 | !agreement.doesDiscountExist(catalogItem))
			return false;
		agreement.setItemQuantityForDiscount(catalogItem, quantity);
		return true;
	}
	*/
	public static boolean removeItemDiscount (Agreement agreement, CatalogItem catalogItem) {
		if (!agreement.doesDiscountExist(catalogItem))
			return false;
		agreement.removeItemDiscount(catalogItem);
		return true;
	}
	public static boolean removeItem (Agreement agreement, CatalogItem catalogItem) {
		if (!agreement.doesDiscountExist(catalogItem))
			return false;
		agreement.removeItem(catalogItem);
		return true;
	}
	
	public static int getPercentItemDiscount (Agreement agreement, CatalogItem catalogItem) {
		if (agreement.doesDiscountExist(catalogItem))
			return agreement.getItemDiscountPercent(catalogItem);
		return -1;			
	}
	
	public static int getItemQuantityForDiscount (Agreement agreement, CatalogItem catalogItem) {
		if (agreement.doesDiscountExist(catalogItem))
			return agreement.getItemQuantityForDiscount(catalogItem);
		return -1;			
	}
	
	public static boolean doesDiscountExist (Agreement agreement, CatalogItem catalogItem) {
		return agreement.doesDiscountExist(catalogItem);
	}
	
	public static String printItemDetails (Agreement agreement, CatalogItem catalogItem) {
		String str = "item quantity for discount = " + agreement.getItemQuantityForDiscount(catalogItem);
		str += " , item discount = " + agreement.getItemDiscountPercent(catalogItem) + "%";
		return str;
	}
}
