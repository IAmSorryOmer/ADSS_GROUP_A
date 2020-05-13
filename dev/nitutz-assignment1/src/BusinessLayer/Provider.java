package BusinessLayer;

import java.util.*;

public class Provider {
	
	//fields
	private String ProviderID;
	private String CreditCardNumber;
	private boolean NeedsTransport;
	private int DelayDays;
	private List<String> ArrivalDays;
	private String Name;
	private CommunicationDetails communicationDetails;
	
	//constructor
	public Provider(String providerID, String CreditCardNumber, boolean NeedsTransport, int DelayDays, List<String> ArrivalDays, String Name, CommunicationDetails CommunicationDetails) {
		this.ProviderID = providerID;
		this.CreditCardNumber = CreditCardNumber;
		this.NeedsTransport = NeedsTransport;
		this.ArrivalDays = ArrivalDays;
		this.Name = Name;
		this.communicationDetails = CommunicationDetails;
	}
	
	
	
	//getters and setters
	public String getProviderID() {
		return ProviderID;
	}

	public String getCreditCardNumber() {
		return CreditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		CreditCardNumber = creditCardNumber;
	}
	public boolean isNeedsTransport() {
		return NeedsTransport;
	}
	public void setNeedsTransport(boolean NeedsTransport) {
		this.NeedsTransport = NeedsTransport;
	}
	public int getDelayDays() {
		return DelayDays;
	}
	public void setDelayDays(int delayDays) {
		DelayDays = delayDays;
	}
	public List<String> getArrivalDays() {
		return ArrivalDays;
	}
	public void setArrivalDays(List<String> arrivalDays) {
		ArrivalDays = arrivalDays;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public CommunicationDetails getCommunicationDetails() {
		return communicationDetails;
	}


}
