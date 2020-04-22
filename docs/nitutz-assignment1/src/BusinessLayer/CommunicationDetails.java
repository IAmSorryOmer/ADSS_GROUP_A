package BusinessLayer;

public class CommunicationDetails {

	private boolean IsFixedDays;
	private String PhoneNum;
	private String Address;
	private Agreement Agreement;
	
	
	//constructor
	private CommunicationDetails(boolean IsFixedDays, String PhoneNum, String Address, Agreement Agreement) {
		this.IsFixedDays = IsFixedDays;
		this.PhoneNum  = PhoneNum;
		this.Address = Address;
		this.Agreement =  Agreement;
	}
	
	//creator
	public CommunicationDetails communicationDetailsCreate(boolean IsFixedDays, String PhoneNum, String Address, Agreement Agreement) {
		CommunicationDetails comm = new CommunicationDetails(IsFixedDays, PhoneNum, Address, Agreement);
		
		return comm;
	}
	//getters and setters
	public boolean isIsFixedDays() {
		return IsFixedDays;
	}

	public void setIsFixedDays(boolean isFixedDays) {
		IsFixedDays = isFixedDays;
	}

	public String getPhoneNum() {
		return PhoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public Agreement getAgreement() {
		return Agreement;
	}

	public void setAgreement(Agreement agreement) {
		Agreement = agreement;
	}
	
	//methods
	public static boolean editDetails(CommunicationDetails comm,boolean IsFixedDays, String PhoneNum, String Address, Agreement Agreement) {
		try {
			comm.setAddress(Address);
			comm.setAgreement(Agreement);
			comm.setIsFixedDays(IsFixedDays);
			comm.setAgreement(Agreement);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

}
