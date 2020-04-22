package BusinessLayer;
import java.util.*;
import java.util.List;
import java.lang.*;


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
	private Provider(String providerID, String CreditCardNumber, boolean DoesNeedTransport, int DelayDays, List<String> ArrivalDays, String Name, CommunicationDetails CommunicationDetails) {
		this.ProviderID = providerID;
		this.CreditCardNumber = CreditCardNumber;
		this.DoesNeedTransport = DoesNeedTransport;
		this.ArrivalDays = ArrivalDays;
		this.Name = Name;
		this.CommunicationDetails = CommunicationDetails;
	}
	
	//creator
	public Provider ProviderCreator(String providerID, String CreditCardNumber, boolean DoesNeedTransport, int DelayDays, List<String> ArrivalDays, String Name, CommunicationDetails CommunicationDetails) {
		Provider provider = new Provider(providerID,CreditCardNumber, DoesNeedTransport, DelayDays, ArrivalDays, Name, CommunicationDetails);
		
		return provider;
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
		return CommunicationDetails;
	}
	public void setCommunicationDetails(CommunicationDetails communicationDetails) {
		CommunicationDetails = communicationDetails;
	}

	//methods
	public static boolean editDetails(Provider provider,boolean DoesNeedTransport, int DelayDays, List<String> ArrivalDays, String Name) {
		try {
		provider.setDoesNeedTransport(DoesNeedTransport);
		provider.setDelayDays(DelayDays);
		provider.setArrivalDays(ArrivalDays);
		return true;
		}
		catch(Exception e) {
			return false;
		}
	}



}
