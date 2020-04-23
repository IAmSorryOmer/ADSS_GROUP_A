package InterfaceLayer;

import java.util.List;

import BusinessLayer.Agreement;
import BusinessLayer.AllProviders;
import BusinessLayer.CommunicationDetails;
import BusinessLayer.Provider;

public class ProviderInterface {
	//Provider
	public Provider ProviderCreator(String providerID, String CreditCardNumber, boolean DoesNeedTransport, int DelayDays, List<String> ArrivalDays, String Name, boolean IsFixedDays, String PhoneNum, String Address, int quantityForDiscount) {
		CommunicationDetails cd;
		if(quantityForDiscount > 0)
			cd = communicationDetailsCreatorWithAgreement(IsFixedDays, PhoneNum, Address, quantityForDiscount);
		else
			cd = communicationDetailsCreatorNoAgreement(IsFixedDays, PhoneNum, Address);
		Provider p = BusinessLayer.Provider.ProviderCreator(providerID, CreditCardNumber, DoesNeedTransport, DelayDays, ArrivalDays, Name, cd);
		return p;
	}
	public static boolean editDetails(String ID,boolean DoesNeedTransport, int DelayDays, List<String> ArrivalDays, String Name) {
		try {
		AllProviders ap = AllProviders.getInstance();
		Provider p = (AllProviders.getProvidersByID(ap, ID).get(0));
		return Provider.editDetails(p, DoesNeedTransport, DelayDays, ArrivalDays, Name);
		}
		catch(Exception e) {
		return false;
		}
	}
	public static String printDetails(String ID) {
		try {
			AllProviders ap = AllProviders.getInstance();
			Provider p = (AllProviders.getProvidersByID(ap, ID).get(0));
			return Provider.printDetails(p);
		}
		catch(Exception e) {
			return "no provider with specified id was found";
			}
	}
	//CommunicationDetails
	public CommunicationDetails communicationDetailsCreatorNoAgreement(boolean IsFixedDays, String PhoneNum, String Address) {
		CommunicationDetails cd = CommunicationDetails.communicationDetailsCreatorNoAgreement(IsFixedDays, PhoneNum, Address);
		return cd;
	}
	public CommunicationDetails communicationDetailsCreatorWithAgreement(boolean IsFixedDays, String PhoneNum, String Address, int quantityForDiscount) {
		CommunicationDetails cd = CommunicationDetails.communicationDetailsCreatorWithAgreement(IsFixedDays, PhoneNum, Address, quantityForDiscount);
		return cd;
	}
	public static boolean editDetails(String ID, boolean ssFixedDays, String phoneNum, String address, int quantityForDiscount) {
		try {
			AllProviders ap = AllProviders.getInstance();
			Provider p = (AllProviders.getProvidersByID(ap, ID).get(0));
			return CommunicationDetails.editDetails(p.getCommunicationDetails(), ssFixedDays, phoneNum, address, quantityForDiscount);
			}
			catch(Exception e) {
			return false;
			}
	}
}
