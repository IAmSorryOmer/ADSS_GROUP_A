package BusinessLayer;

import java.awt.geom.AffineTransform;
import java.util.List;

public class ProviderController {

	public static List<Provider> providerList;
	
	//creator
	public static Provider ProviderCreator(String ProviderID, String CreditCardNumber, boolean DoesNeedTransport, int DelayDays, List<String> ArrivalDays, String Name,  boolean IsFixedDays, String PhoneNum, String Address, boolean isAgreement) {
		CommunicationDetails commDetails = CommunicationDetailsController.communicationDetailsCreator(ProviderID, IsFixedDays, PhoneNum, Address, isAgreement);
		Provider provider = new Provider(ProviderID, CreditCardNumber, DoesNeedTransport, DelayDays, ArrivalDays, Name, commDetails);
		providerList.add(provider);
		return provider;
	}
	
	
	//methods
	public static boolean editDetails(Provider provider,boolean NeedsTransport, int DelayDays, List<String> ArrivalDays, String Name, boolean isFixedDays, String phoneNum, String address) {
		try {
		provider.setNeedsTransport(NeedsTransport);
		provider.setDelayDays(DelayDays);
		provider.setArrivalDays(ArrivalDays);
		return CommunicationDetailsController.editDetails(provider.getCommunicationDetails(), isFixedDays, phoneNum, address);
		}
		catch(Exception e) {
			return false;
		}
	}
	
	/*
	 * @return returns provider by id, or null if no such provider exists
	 */
	public static Provider getProvierByID (String providerID) {
		for (Provider provider : providerList) {
			if (provider.getProviderID().compareTo(providerID) == 0)
				return provider;
		}
		return null;
	}
	
	public static boolean addCatalogItem (Provider provider, String catalogNum, Product describedProduct, double price) {
		CatalogItem catalogItem = CatalogItemController.CatalogItemCreator(provider.getProviderID(), catalogNum, describedProduct, price);
		return CommunicationDetailsController.addCatalogItem(provider.getCommunicationDetails(), catalogItem);
	}
	
	
	public static boolean editCatalogItem (Provider provider, CatalogItem catalogItem, double price, int quantity, int discount) {
		boolean ans = true;
		ans = CatalogItemController.editPrice(catalogItem, price);
		if (ans) {
			ans = AgreementController.editItem(provider.getCommunicationDetails().getAgreement(), catalogItem, quantity, discount);
		}
		return ans;
	}
	
	public static boolean removeCatalogItem (Provider provider, CatalogItem catalogItem) {
		return CommunicationDetailsController.removeCatalogItem(provider.getCommunicationDetails(), catalogItem);	
	}
	
	public static String printDetails(Provider p) {
		String output = "provider details:\n";
		output += "ID: " + p.getProviderID() + "\n";
		output += "CreditCardNum: " + p.getCreditCardNumber() + "\n";
		output += "need transport: "+p.isNeedsTransport() + "\n";
		if(!p.getCommunicationDetails().getIsFixedDays())
			output += "Delay Untill Delivery: " + p.getDelayDays() + " Days\n";
		else {
			output += "Comes in days: ";
			for(String s : p.getArrivalDays())
				output += s+", ";
			output = output.substring(0, output.length()-2) + "\n";
		}
		output += "Name: "+p.getName() + "\n";
		output += CommunicationDetailsController.printDetails(p.getCommunicationDetails())+"\n";
		return output;
	}
}
