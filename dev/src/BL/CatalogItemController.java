package BL;

import DAL.CatalogItemDAL;
import Entities.CatalogItem;
import Entities.ProductDetails;
import Entities.Provider;

import java.util.concurrent.ExecutionException;

public class CatalogItemController {

	//creators
	public static void CatalogItemCreator(CatalogItem catalogItem, ProductDetails productDetails) {
		if(getCatalogItemById(catalogItem.getCatalogNum()) != null)
			throw new IllegalArgumentException("there is already catalog item with id " + catalogItem.getCatalogNum());
		catalogItem.setProductDetails(productDetails);
		CatalogItemDAL.insertItem(catalogItem);
	}
	
	public static CatalogItem getCatalogItemById(String catalogNum){
		return CatalogItemDAL.getCatalogItemById(catalogNum);
	}

	//methods
	public static void editCatalogItem(Provider provider, String catalogNum, double price) {
		CatalogItem catalogItem = getCatalogItemById(catalogNum);
		if(catalogItem == null || !catalogItem.getProviderID().equals(provider.getProviderID()))
			throw new IllegalArgumentException("there is no item with id " + catalogNum + " for provider number " + provider.getProviderID());
		if (price <= 0)
			throw new IllegalArgumentException("price should be positive");
		catalogItem.setPrice(price);
		CatalogItemDAL.editItem(catalogItem);
	}

	public static void removeItem(Provider provider, String catalogItemId){
		CatalogItem catalogItem = getCatalogItemById(catalogItemId);
		if(catalogItem == null || !catalogItem.getProviderID().equals(provider.getProviderID()))
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
