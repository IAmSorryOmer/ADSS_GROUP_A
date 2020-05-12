package InterfaceLayer;

import java.util.List;

import BusinessLayer.Agreement;
import BusinessLayer.AllProviders;
import BusinessLayer.CatalogItem;
import BusinessLayer.CommunicationDetails;
import BusinessLayer.Product;
import BusinessLayer.Provider;

public class ProviderInterface {
	//Provider
	public static Provider ProviderCreator(String providerID, String CreditCardNumber, boolean DoesNeedTransport, int DelayDays, List<String> ArrivalDays, String Name, boolean IsFixedDays, String PhoneNum, String Address, int quantityForDiscount) {
		CommunicationDetails cd;
		if(quantityForDiscount > 0)
			cd = communicationDetailsCreatorWithAgreement(IsFixedDays, PhoneNum, Address, quantityForDiscount);
		else
			cd = communicationDetailsCreatorNoAgreement(IsFixedDays, PhoneNum, Address);
		Provider p = BusinessLayer.ProviderController.ProviderCreator(providerID, CreditCardNumber, DoesNeedTransport, DelayDays, ArrivalDays, Name, cd);
		return p;
	}
	public static boolean editDetails(String ID,boolean DoesNeedTransport, int DelayDays, List<String> ArrivalDays, String Name) {
		try {
		Provider p = (AllProviders.getInstance().getProvidersByID(ID));
		return Provider.editDetails(p, DoesNeedTransport, DelayDays, ArrivalDays, Name);
		}
		catch(Exception e) {
		return false;
		}
	}
	public static String printDetails(String ID) {
		try {
			Provider p = (AllProviders.getInstance().getProvidersByID(ID));
			return Provider.printDetails(p);
		}
		catch(Exception e) {
			return "no provider with specified id was found";
			}
	}
	//CommunicationDetails
	public static CommunicationDetails communicationDetailsCreatorNoAgreement(boolean IsFixedDays, String PhoneNum, String Address) {
		CommunicationDetails cd = CommunicationDetails.communicationDetailsCreatorNoAgreement(IsFixedDays, PhoneNum, Address);
		return cd;
	}
	public static CommunicationDetails communicationDetailsCreatorWithAgreement(boolean IsFixedDays, String PhoneNum, String Address, int quantityForDiscount) {
		CommunicationDetails cd = CommunicationDetails.communicationDetailsCreatorWithAgreement(IsFixedDays, PhoneNum, Address, quantityForDiscount);
		return cd;
	}
	public static boolean editDetails(String ID, boolean ssFixedDays, String phoneNum, String address, int quantityForDiscount) {
		try {
			Provider p = (AllProviders.getInstance().getProvidersByID(ID));
			return CommunicationDetails.editDetails(p.getCommunicationDetails(), ssFixedDays, phoneNum, address, quantityForDiscount);
			}
			catch(Exception e) {
			return false;
			}
	}
	public static String printItems(String ID) {
		try {
			Provider p = (AllProviders.getInstance().getProvidersByID(ID));
			CommunicationDetails cd = p.getCommunicationDetails();
			return CommunicationDetails.printDetails(cd);
			}
			catch(Exception e) {
			return "no provider with this id";
			}
	}
	
	public static boolean addItem(String providerID, int discount, String catalogNum, Product describedProduct, double price) {
		try {
			Provider p = (AllProviders.getInstance().getProvidersByID(providerID));
			CatalogItem c = CatalogItem.CatalogItemCreator(discount, catalogNum, describedProduct, price);
			return CommunicationDetails.addCatalogItem(p.getCommunicationDetails(), c);
		}
		catch(Exception e) {
		return false;
		}
	}
}
