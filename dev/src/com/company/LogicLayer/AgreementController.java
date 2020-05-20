package com.company.LogicLayer;

import com.company.DataAccessLayer.AgreementDAL;
import com.company.Entities.Agreement;
import com.company.Entities.CatalogItem;
import com.company.Entities.CommunicationDetails;
import com.company.Entities.Provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgreementController {

	//creators
	public static Agreement AgreementCreator(Agreement agreement) {
		Provider provider = ProviderController.getProvierByID(agreement.getProviderID());
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + agreement.getProviderID());
		provider.getCommunicationDetails().setAgreement(agreement);
		return agreement;
	}

	public static void addItem(String providerId, String catalogItemId, int minAmount, double discount) {
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		CatalogItem catalogItem = CatalogItemController.getCatalogItemById(provider, catalogItemId);
		if(catalogItem == null)
			throw new IllegalArgumentException("there is no catalog item with id " + catalogItemId + " for provider with id " + providerId);
		if(minAmount < 0 || discount < 0){
			throw new IllegalArgumentException("illegal arguments when adding item to agreement");
		}
		Agreement agreement = provider.getCommunicationDetails().getAgreement();
		if(agreement == null)
			throw new IllegalArgumentException("this provider doesnt have an agreement.");
		if(agreement.doesDiscountExist(catalogItem))
			throw new IllegalArgumentException("there is already item with id " + catalogItemId + " in this agreement. you can edit him instead");
		agreement.addItem(catalogItem, minAmount, discount);
	}
	
	public static void editItem (String providerId, String catalogItemId, Integer minAmount, Double discount) {
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		CatalogItem catalogItem = CatalogItemController.getCatalogItemById(provider, catalogItemId);
		if(catalogItem == null)
			throw new IllegalArgumentException("there is no catalog item with id " + catalogItemId + " for provider with id " + providerId);
		if((minAmount != null && minAmount < 0) || (discount != null && discount < 0)){
			throw new IllegalArgumentException("illegal arguments when adding item to agreement");
		}
		Agreement agreement = provider.getCommunicationDetails().getAgreement();
		if(agreement == null)
			throw new IllegalArgumentException("this provider doesnt have an agreement.");
		if(!agreement.doesDiscountExist(catalogItem))
			throw new IllegalArgumentException("there isnt item with id " + catalogItemId + " in this agreement.");
		if(minAmount != null){
			agreement.setItemQuantityForDiscount(catalogItem, minAmount);
		}
		if(discount != null){
			agreement.setItemDiscountPercent(catalogItem, discount);
		}
	}
	public static void removeItemByIds(String providerId, String catalogItemId){
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		CatalogItem catalogItem = CatalogItemController.getCatalogItemById(provider, catalogItemId);
		if(catalogItem == null)
			throw new IllegalArgumentException("there is no catalog item with id " + catalogItemId + " for provider with id " + providerId);
		removeItem(provider, catalogItem);
	}

	public static void removeItem (Provider provider, CatalogItem catalogItem) {
		Agreement agreement = provider.getCommunicationDetails().getAgreement();
		if(agreement == null)
			throw new IllegalArgumentException("this provider doesnt have an agreement.");
		agreement.removeItem(catalogItem);
	}
	
	public static double getPercentItemDiscount (Agreement agreement, CatalogItem catalogItem) {
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

	private static void ensureUpdated(Agreement agreement){
		if(!agreement.isUpdated()){
			//TODO update from database
		}
	}

	public static String stringifyAgreementItems(String providerId){
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		if(!provider.getCommunicationDetails().isAgreement()){
			throw new IllegalArgumentException("the provider with id " + providerId + " doesnt have an agreement");
		}
		Agreement agreement = provider.getCommunicationDetails().getAgreement();
		int counter = 1;
		StringBuilder toReturn = new StringBuilder();
		for(Map.Entry<CatalogItem, Integer> entry : agreement.getItemQuantityForDiscount().entrySet()){
			CatalogItem catalogItem = entry.getKey();
			int minAmount = entry.getValue();
			double discount = agreement.getItemDiscountPercent(catalogItem);
			toReturn.append(counter).append(") catalog number: ").append(catalogItem.getCatalogNum()).append(", item name: ").append(catalogItem.GetDescribedProductName()).
					append(", min amount to discount: ").append(minAmount).append(", discount: ").append(discount).append("\n");
			counter++;
		}
		return toReturn.toString();
	}
	public static String printItemDetails (Agreement agreement, CatalogItem catalogItem) {
		String str = "item quantity for discount = " + agreement.getItemQuantityForDiscount(catalogItem);
		str += " , item discount = " + agreement.getItemDiscountPercent(catalogItem) + "%";
		return str;
	}
}
