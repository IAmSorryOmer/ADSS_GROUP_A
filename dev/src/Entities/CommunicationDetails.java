package Entities;


import java.util.LinkedList;
import java.util.List;

public class CommunicationDetails {

	//fields
	private Provider Provider;
	private boolean IsFixedDays;
	private String PhoneNum;
	private String Address;
	private Agreement agreement;
	//TODO when loading catalog items, load to this list
	private List<CatalogItem> catalogItems; //details of the produts included in the agreement wiht the provider
	private boolean updated;
	
	//constructor
	public CommunicationDetails(Provider Provider, boolean IsFixedDays, String PhoneNum, String Address, Agreement agreement) {
		this.Provider = Provider;
		this.IsFixedDays = IsFixedDays;
		this.PhoneNum  = PhoneNum;
		this.Address = Address;
		this.agreement = agreement;
		this.catalogItems = new LinkedList<CatalogItem>();
		this.updated = false;
	}

	//getters and setters
	public Provider getProvider() {
		return Provider;
	}
	
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

	public boolean isFixedDays() {
		return IsFixedDays;
	}

	public void setFixedDays(boolean fixedDays) {
		IsFixedDays = fixedDays;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public void setCatalogItems(List<CatalogItem> catalogItems) {
		this.catalogItems = catalogItems;
	}

	public boolean isAgreement() {
		return (this.agreement != null);
	}
	
	public List<CatalogItem> getCatalogItems(){
		return this.catalogItems;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setProvider(Entities.Provider provider) {
		Provider = provider;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
}
