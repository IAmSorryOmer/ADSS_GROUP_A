package BusinessLayer;

import java.util.List;

public class CommunicationDetailsController {
	         
	public static List<CommunicationDetails> communicationDetailsList;
	
	
	//creator
	public static CommunicationDetails communicationDetailsCreatorWithAgreement(boolean IsFixedDays, String PhoneNum, String Address, boolean isAgreement) {
		CommunicationDetails commDetails = new CommunicationDetails(IsFixedDays, PhoneNum, Address, isAgreement);
		return commDetails;
	}	
	
	
	//methods
	public static boolean editDetails (CommunicationDetails commDetails, boolean isFixedDays, String phoneNum, String address) {
		commDetails.setIsFixedDays(isFixedDays);	
		commDetails.setPhoneNum(phoneNum);
		commDetails.setAddress(address);
		return true;
	}
	
	public static String printDetails (CommunicationDetails commDetails) {
		String output = "Phone: "+commDetails.getPhoneNum() + "\n";
		output += "Address: " + commDetails.getAddress() + "\n";
		output += "Items: \n";
		for (CatalogItem catalogItem : commDetails.getCatalogItems()) {
			output += CatalogItem.printItem(catalogItem);
			if (commDetails.isAgreement())
				output += ", " + AgreementController.printItemDetails(commDetails.getAgreement(), catalogItem) + "\n";
		}
		return output;
	}
	
	public static CatalogItem getItemByID (CommunicationDetails commDetails, String CatalogItemID) {
		for(CatalogItem catalogItem : commDetails.getCatalogItems())
			if(catalogItem.getCatalogNum().equals(CatalogItemID))
				return catalogItem;
		return null;
	}
	
	public static boolean addCatalogItem (CommunicationDetails commDetails, CatalogItem catalogItem) {
		boolean ans = commDetails.getCatalogItems().add(catalogItem);
		if (ans & commDetails.isAgreement())
			AgreementController.addItem(commDetails.getAgreement(), catalogItem);
		return ans;
	}
	
	public static boolean addCatalogItem (CommunicationDetails commDetails, CatalogItem catalogItem, int quantityFOrDiscount, int discountPercent) {
		boolean ans = commDetails.getCatalogItems().add(catalogItem);
		if (ans & commDetails.isAgreement())
			AgreementController.addItemDiscount(commDetails.getAgreement(), catalogItem, quantityFOrDiscount, discountPercent);
		return ans;
	}
	
	public static boolean removeCatalogItem (CommunicationDetails commDetails, CatalogItem catalogItem) {
		boolean ans = commDetails.getCatalogItems().remove(catalogItem);
		if (commDetails.isAgreement()) {
			if (ans & AgreementController.doesDiscountExist(commDetails.getAgreement(), catalogItem)) {
				ans = AgreementController.removeItem(commDetails.getAgreement(),catalogItem);
				if (!ans)	//if removal from agreemnet failed
					commDetails.getCatalogItems().add(catalogItem);
			}
		}
		return ans;
	}
	
}
