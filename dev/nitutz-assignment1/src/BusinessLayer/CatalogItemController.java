package BusinessLayer;

import java.util.List;

public class CatalogItemController {

	public static List<CatalogItem> catalogItemList;
	
	
	//creators
	public static CatalogItem CatalogItemCreator (String ProviderID, String catalogNum, Product describedProduct, double price) {
		CatalogItem catalogItem = new CatalogItem(ProviderID, catalogNum, describedProduct, price);
		catalogItemList.add(catalogItem);
		return catalogItem;
	}
	
	
	//methods
	public static boolean editPrice (CatalogItem catalogItem, double price) {
		if (price < 0)
			return false;
		catalogItem.setPrice(price);
		return true;
	}
	
 	public static String printItem(CatalogItem c) {
		String s = "Catalog-Number: " + c.getCatalogNum() + ", ";
		s += "Item name:" + c.GetDescribedProductName() + ", ";
		s += "Regular price: " + c.getPrice();
		return s;
	}
}
