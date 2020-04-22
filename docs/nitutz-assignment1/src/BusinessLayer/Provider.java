package BusinessLayer;

import java.util.List;

public class Provider {
	
	//fields
	private String ProviderID;
	private String CreditCardNumber;
	private boolean DoesNeedTransport;
	private int DelayDays;
	private List<String> ArrivalDays;
	private String Name;
	private CommunicationDetails CommunicationDetails;
	
	//constructor
	private Provider() {}
	
	//creator
	public Provider ProviderCreator() {
		
	}
	
	//getters and setters
	public String getProviderID() {
		return ProviderID;
	}
	public void setProviderID(String providerID) {
		ProviderID = providerID;
	}
	public String getCreditCardNumber() {
		return CreditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		CreditCardNumber = creditCardNumber;
	}
	public boolean isDoesNeedTransport() {
		return DoesNeedTransport;
	}
	public void setDoesNeedTransport(boolean doesNeedTransport) {
		DoesNeedTransport = doesNeedTransport;
	}
	public int getDelayDays() {
		return DelayDays;
	}
	public void setDelayDays(int delayDays) {
		DelayDays = delayDays;
	}
	public String[] getArrivalDays() {
		return ArrivalDays;
	}
	public void setArrivalDays(String[] arrivalDays) {
		ArrivalDays = arrivalDays;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public CommunicationDetails getCommunicationDetails() {
		return CommunicationDetails;
	}
	public void setCommunicationDetails(CommunicationDetails communicationDetails) {
		CommunicationDetails = communicationDetails;
	}

	//methods
	



}
