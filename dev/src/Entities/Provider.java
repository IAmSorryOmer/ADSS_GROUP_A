package Entities;

public class Provider {
	public static final int SUNDAY = 1<<0;
	public static final int MONDAY = 1<<1;
	public static final int TUESDAY = 1<<2;
	public static final int WEDNESDAY = 1<<3;
	public static final int THURSDAY = 1<<4;
	public static final int FRIDAY = 1<<5;
	public static final int SATURDAY = 1<<6;


	//fields
	private String ProviderID;
	private String CreditCardNumber;
	private boolean NeedsTransport;
	private int DelayDays;
	private int ArrivalDays;
	private String Name;
	private CommunicationDetails communicationDetails;
	
	//constructor
	public Provider(String providerID, String CreditCardNumber, boolean NeedsTransport, int DelayDays, int ArrivalDays, String Name, CommunicationDetails CommunicationDetails) {
		this.ProviderID = providerID;
		this.CreditCardNumber = CreditCardNumber;
		this.NeedsTransport = NeedsTransport;
		this.DelayDays = DelayDays;
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
	public int getArrivalDays() {
		return ArrivalDays;
	}
	public void setArrivalDays(int arrivalDays) {
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

	public void setProviderID(String providerID) {
		ProviderID = providerID;
	}

	public void setCommunicationDetails(CommunicationDetails communicationDetails) {
		this.communicationDetails = communicationDetails;
	}

	@Override
	public String toString() {
		String output = "provider details:\n";
		output += "ID: " + ProviderID + "\n";
		output += "CreditCardNum: " + CreditCardNumber + "\n";
		output += "need transport: "+ NeedsTransport + "\n";
		if(!communicationDetails.getIsFixedDays())
			output += "Delay Untill Delivery: " + DelayDays + " Days\n";
		else {
			output += "Comes in days: " + stringifyArrivalDays() + "\n";
		}
		output += "Name: "+ Name + "\n";
		return output;
	}

	public boolean isWorkingAtDay(int day){
		return (ArrivalDays & (1 << day)) != 0;
	}
	public String stringifyArrivalDays(){
		String arr[] = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		String result = "";
		for(int i = 0; i< 7;i++){
			if(isWorkingAtDay(i))
				result += arr[i] + ", ";
		}
		if(result.length() != 0)
			result = result.substring(0,result.length()-2);
		return result;
	}

}
