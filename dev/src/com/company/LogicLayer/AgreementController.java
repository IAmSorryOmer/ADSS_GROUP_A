package com.company.LogicLayer;

import com.company.DataAccessLayer.AgreementDAL;
import com.company.Entities.Agreement;
import com.company.Entities.CatalogItem;
import com.company.Entities.Pair;
import com.company.Entities.Provider;

import java.util.Map;

public class AgreementController {

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
		if(isDiscountExist(catalogItem,provider))
			throw new IllegalArgumentException("there is already item with id " + catalogItemId + " in this agreement. you can edit him instead");
		provider.getCommunicationDetails().getAgreement().addItem(catalogItem, minAmount, discount);
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
		if(!isDiscountExist(catalogItem,provider))
			throw new IllegalArgumentException("there isnt item with id " + catalogItemId + " in this agreement.");
		if(minAmount != null){
			provider.getCommunicationDetails().getAgreement().setItemQuantityForDiscount(catalogItem, minAmount);
		}
		if(discount != null){
			provider.getCommunicationDetails().getAgreement().setItemDiscountPercent(catalogItem, discount);
		}
		AgreementDAL.editItem(catalogItem, discount, minAmount);
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
		if(!isDiscountExist(catalogItem,provider))
			throw new IllegalArgumentException("there isnt item with id " + catalogItem.getCatalogNum() + " in this agreement.");
		provider.getCommunicationDetails().getAgreement().removeItem(catalogItem);
		AgreementDAL.removeItem(catalogItem);
	}

	public static boolean isDiscountExist(CatalogItem catalogItem, Provider provider){
		if(!provider.getCommunicationDetails().isAgreement() || !provider.getCommunicationDetails().getAgreement().isUpdated()){
			AgreementDAL.loadProviderAgreement(provider);
		}
		return provider.getCommunicationDetails().getAgreement().getAgreementItems().containsKey(catalogItem);
	}
	public static Agreement getProviderAgreement(Provider provider){
		if(!provider.getCommunicationDetails().isAgreement() || !provider.getCommunicationDetails().getAgreement().isUpdated()){
			AgreementDAL.loadProviderAgreement(provider);
		}
		return provider.getCommunicationDetails().getAgreement();
	}

	public static String stringifyAgreementItems(String providerId){
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		if(!provider.getCommunicationDetails().isAgreement()){
			throw new IllegalArgumentException("the provider with id " + providerId + " doesnt have an agreement");
		}
		Agreement agreement = getProviderAgreement(provider);
		int counter = 1;
		StringBuilder toReturn = new StringBuilder();
		for(Map.Entry<CatalogItem, Pair<Integer,Double>> entry : agreement.getAgreementItems().entrySet()){
			CatalogItem catalogItem = entry.getKey();
			int minAmount = entry.getValue().getFirst();
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
