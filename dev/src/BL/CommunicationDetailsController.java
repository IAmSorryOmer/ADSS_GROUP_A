package BL;

import DAL.CatalogItemDAL;
import Entities.Agreement;
import Entities.CatalogItem;
import Entities.CommunicationDetails;
import Entities.Provider;

import java.util.List;

public class CommunicationDetailsController {

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
		String output = "Phone: "+commDetails.getPhoneNum() + "\n";
		output += "Address: " + commDetails.getAddress() + "\n";
		output += "Items: \n";
		Agreement agreement = AgreementController.getProviderAgreement(commDetails.getProvider());
		for (CatalogItem catalogItem : getProviderItems(commDetails.getProvider())) {
			output += CatalogItemController.printItem(catalogItem);
			//TODO agreement shit
			if (agreement != null && agreement.doesDiscountExist(catalogItem))
				output += ", " + AgreementController.printItemDetails(commDetails.getAgreement(), catalogItem) + "\n";
		}
		return output;
	}

	public static CatalogItem getItemByID (CommunicationDetails commDetails, String CatalogItemID) {
		return CatalogItemDAL.getCatalogItemByIdAndProvider(commDetails.getProvider().getProviderID(), CatalogItemID);
	}

	public static CatalogItem getItemByProductDetails(CommunicationDetails commDetails, String productDetailsId){
		return CatalogItemDAL.getCatalogItemByProductDetail(commDetails.getProvider().getProviderID(), productDetailsId);
	}
	
	public static void addCatalogItem (CommunicationDetails commDetails, CatalogItem catalogItem) {
		if(getItemByID(commDetails, catalogItem.getCatalogNum()) != null){
			throw new IllegalArgumentException("there is already an item with number " + catalogItem.getCatalogNum() + " for provider number " + commDetails.getProvider().getProviderID());
		}
		if(commDetails.isUpdated()) {
			//if not updated then in the next access everything will be overwritten so that pointless
			commDetails.getCatalogItems().add(catalogItem);
		}
	}

	public static void removeCatalogItem (CommunicationDetails commDetails, CatalogItem catalogItem) {
		commDetails.getCatalogItems().remove(catalogItem);
		if (commDetails.isAgreement()) {
			if(AgreementController.isDiscountExist(catalogItem, commDetails.getProvider()))
				AgreementController.removeItem(commDetails.getProvider(), catalogItem);
		}
	}

	public static List<CatalogItem> getProviderItems(Provider provider){
		if(!provider.getCommunicationDetails().isUpdated()){
			CatalogItemDAL.loadProviderItems(provider.getCommunicationDetails());
		}
		return provider.getCommunicationDetails().getCatalogItems();
	}
}
