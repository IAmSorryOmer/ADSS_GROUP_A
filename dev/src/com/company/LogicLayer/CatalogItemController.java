package com.company.LogicLayer;

import com.company.DataAccessLayer.CatalogItemDAL;
import com.company.Entities.CatalogItem;
import com.company.Entities.CommunicationDetails;
import com.company.Entities.ProductDetails;
import com.company.Entities.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
