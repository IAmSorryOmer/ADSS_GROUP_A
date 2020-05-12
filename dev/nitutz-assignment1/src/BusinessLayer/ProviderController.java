package BusinessLayer;

import java.util.List;

public class ProviderController {

	public static List<Provider> providerList;
	
	//creator
	public static Provider ProviderCreator(String providerID, String CreditCardNumber, boolean DoesNeedTransport, int DelayDays, List<String> ArrivalDays, String Name, CommunicationDetails CommunicationDetails) {
		Provider provider = new Provider(providerID,CreditCardNumber, DoesNeedTransport, DelayDays, ArrivalDays, Name, CommunicationDetails);
		providerList.add(provider);
		return provider;
	}
}
