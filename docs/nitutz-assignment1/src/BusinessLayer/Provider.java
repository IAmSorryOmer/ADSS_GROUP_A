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
	private CommunicationDetails communicationDetails;
	
	//constructor
	private Provider(String providerID, String CreditCardNumber, boolean DoesNeedTransport, int DelayDays, List<String> ArrivalDays, String Name, CommunicationDetails CommunicationDetails) {
		this.ProviderID = providerID;
		this.CreditCardNumber = CreditCardNumber;
		this.DoesNeedTransport = DoesNeedTransport;
		this.ArrivalDays = ArrivalDays;
		this.Name = Name;
		this.communicationDetails = CommunicationDetails;
	}
	
	//creator
	public static Provider ProviderCreator(String providerID, String CreditCardNumber, boolean DoesNeedTransport, int DelayDays, List<String> ArrivalDays, String Name, CommunicationDetails CommunicationDetails) {
		Provider provider = new Provider(providerID,CreditCardNumber, DoesNeedTransport, DelayDays, ArrivalDays, Name, CommunicationDetails);
		AllProviders ap = AllProviders.getInstance();
		AllProviders.addProvider(ap, provider);
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
		return communicationDetails;
	}
	public void setCommunicationDetails(CommunicationDetails communicationDetails) {
		communicationDetails = communicationDetails;
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
	public static String printDetails(Provider p) {
		String output = "provider details:\n";
		output += "ID: " + p.ProviderID + "\n";
		output += "CreditCardNum: " + p.CreditCardNumber + "\n";
		output += "need transport: "+p.DoesNeedTransport + "\n";
		if(!p.getCommunicationDetails().getIsFixedFays())
			output += "Delay Untill Delivery: " + p.DelayDays + " Days\n";
		else {
			output += "Comes in days: ";
			for(String s : p.ArrivalDays)
				output += s+", ";
			output = output.substring(0, output.length()-2) + "\n";
		}
		output += "Name: "+p.Name + "\n";
		output += CommunicationDetails.printDetails(p.communicationDetails)+"\n";
		output += CommunicationDetails.printItems(p.communicationDetails);
		return output;
	}
	



}
