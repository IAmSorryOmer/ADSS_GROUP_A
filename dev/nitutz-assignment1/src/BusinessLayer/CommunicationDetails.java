package BusinessLayer;

import java.util.LinkedList;
import java.util.List;

public class CommunicationDetails {

	//fields
	private boolean IsFixedDays;
	private String PhoneNum;
	private String Address;
	private Agreement agreement;
	private List<CatalogItem> catalogItems; //details of the produts included in the agreement wiht the provider
	
	
	//constructor
	public CommunicationDetails(boolean IsFixedDays, String PhoneNum, String Address, boolean isAgreement) {
		this.IsFixedDays = IsFixedDays;
		this.PhoneNum  = PhoneNum;
		this.Address = Address;
		if (isAgreement)
			this.agreement = BusinessLayer.AgreementController.AgreementCreator();
		else
			this.agreement = null;
		this.catalogItems = new LinkedList<CatalogItem>();
	}
	
	
	//getters and setters
	public boolean getIsFixedDays() {
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
		return agreement;
	}
	
	public boolean isAgreement() {
		return (this.agreement != null);
	}
	
	public List<CatalogItem> getCatalogItems(){
		return this.catalogItems;
	}
	


}
