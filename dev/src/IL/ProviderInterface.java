package IL;

import Entities.CommunicationDetails;
import Entities.Provider;
import BL.AgreementController;
import BL.CatalogItemController;
import BL.ProviderController;

import java.util.List;


public class ProviderInterface {

	//Provider
	public static void ProviderCreator(Provider provider, CommunicationDetails communicationDetails) {
		ProviderController.ProviderCreator(provider, communicationDetails);
	}
	public static void editDetails(String providerId,Boolean NeedsTransport, Integer DelayDays, Integer ArrivalDays, String Name, Boolean isFixedDays, String phoneNum, String address) {
		ProviderController.editDetails(providerId,NeedsTransport,DelayDays,ArrivalDays,Name,isFixedDays,phoneNum, address);
	}
	public static String printDetails(String ID) {
		return ProviderController.printDetails(ID);
	}

	public static List<Provider> getAllProviders(){
		return ProviderController.getAllProviders();
	}
}
