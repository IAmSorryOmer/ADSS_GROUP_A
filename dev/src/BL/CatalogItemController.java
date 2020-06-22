package BL;

import DAL.CatalogItemDAL;
import Entities.CatalogItem;
import Entities.ProductDetails;
import Entities.Provider;

public class CatalogItemController {

	//creators
	public static void CatalogItemCreator(Provider provider, CatalogItem catalogItem, ProductDetails productDetails) {
		catalogItem.setProductDetails(productDetails);
		CatalogItemDAL.insertItem(catalogItem);
	}
	
	public static CatalogItem getCatalogItemById(Provider provider, String catalogNum){
		return CommunicationDetailsController.getItemByID(provider.getCommunicationDetails(), catalogNum);
	}

	//methods
	public static void editCatalogItem(Provider provider, String catalogNum, double price) {
		CatalogItem catalogItem = getCatalogItemById(provider, catalogNum);
		if(catalogItem == null)
			throw new IllegalArgumentException("there is no item with id " + catalogNum + " for provider number " + provider.getProviderID());
		if (price <= 0)
			throw new IllegalArgumentException("price should be positive");
		catalogItem.setPrice(price);
		CatalogItemDAL.editItem(catalogItem);
	}

	public static void removeItem(Provider provider, String catalogItemId){
		CatalogItem catalogItem = getCatalogItemById(provider, catalogItemId);
		if(catalogItem == null)
			throw new IllegalArgumentException("there is no item with id " + catalogItemId + " for provider number " + provider.getProviderID());
		CommunicationDetailsController.removeCatalogItem(provider.getCommunicationDetails(), catalogItem);
	}

 	public static String printItem(CatalogItem c) {
		String s = "Catalog-Number: " + c.getCatalogNum() + ", ";
		s += "Item name:" + c.GetDescribedProductName() + ", ";
		s += "Regular price: " + c.getPrice();
		return s;
	}
}
