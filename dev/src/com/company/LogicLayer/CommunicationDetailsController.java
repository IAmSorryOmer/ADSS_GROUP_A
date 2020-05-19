package com.company.LogicLayer;

import com.company.Entities.Agreement;
import com.company.Entities.CatalogItem;
import com.company.Entities.CommunicationDetails;
import com.company.Entities.Provider;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CommunicationDetailsController {
	         
	public static List<CommunicationDetails> communicationDetailsList = new LinkedList<>();

	//creator
	public static void communicationDetailsCreator(CommunicationDetails communicationDetails) {
		communicationDetailsList.add(communicationDetails);
	}
	
	
	//methods
	public static void editDetails (Provider provider, Boolean isFixedDays, String phoneNum, String address) {
		CommunicationDetails commDetails = provider.getCommunicationDetails();
		if(isFixedDays != null) {
			commDetails.setIsFixedDays(isFixedDays);
		}
		if(phoneNum != null) {
			commDetails.setPhoneNum(phoneNum);
		}
		if(address != null) {
			commDetails.setAddress(address);
		}
	}
	
	public static String printDetails (CommunicationDetails commDetails) {
		ensureUpdated(commDetails);
		String output = "Phone: "+commDetails.getPhoneNum() + "\n";
		output += "Address: " + commDetails.getAddress() + "\n";
		output += "Items: \n";
		for (CatalogItem catalogItem : commDetails.getCatalogItems()) {
			output += CatalogItemController.printItem(catalogItem);
			if (commDetails.isAgreement() && commDetails.getAgreement().doesDiscountExist(catalogItem))
				output += ", " + AgreementController.printItemDetails(commDetails.getAgreement(), catalogItem) + "\n";
		}
		return output;
	}
	
	public static CatalogItem getItemByID (CommunicationDetails commDetails, String CatalogItemID) {
		ensureUpdated(commDetails);
		for(CatalogItem catalogItem : commDetails.getCatalogItems())
			if(catalogItem.getCatalogNum().equals(CatalogItemID))
				return catalogItem;
		return null;
	}

	public static CatalogItem getItemByProductDetails(CommunicationDetails commDetails, String productDetailsId){
		ensureUpdated(commDetails);
		for(CatalogItem catalogItem : commDetails.getCatalogItems())
			if(catalogItem.getProductDetails().getId().equals(productDetailsId))
				return catalogItem;
		return null;
	}
	
	public static void addCatalogItem (CommunicationDetails commDetails, CatalogItem catalogItem) {
		ensureUpdated(commDetails);
		if(getItemByID(commDetails, catalogItem.getCatalogNum()) != null){
			throw new IllegalArgumentException("there is already an item with number " + catalogItem.getCatalogNum() + " for provider number " + commDetails.getProvider().getProviderID());
		}
		commDetails.getCatalogItems().add(catalogItem);
		//TODO
//		boolean ans = commDetails.getCatalogItems().add(catalogItem);
//		if (ans & commDetails.isAgreement())
//			AgreementController.addItem(commDetails.getAgreement(), catalogItem);
	}

	public static void removeCatalogItem (CommunicationDetails commDetails, CatalogItem catalogItem) {
		ensureUpdated(commDetails);
		commDetails.getCatalogItems().remove(catalogItem);
		if (commDetails.isAgreement()) {
			if(AgreementController.doesDiscountExist(commDetails.getAgreement(), catalogItem))
				AgreementController.removeItem(commDetails.getProvider(), catalogItem);
		}
	}
	private static void ensureUpdated(CommunicationDetails communicationDetails){
		if(!communicationDetails.isUpdated()){
			//TODO update from database
		}
	}
}
