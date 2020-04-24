package BusinessLayer;

import java.util.LinkedList;
import java.util.List;

public class CommunicationDetails {

	//fields
	private boolean IsFixedDays;
	private String PhoneNum;
	private String Address;
	private Agreement Agreement;
	private List<CatalogItem> catalogItems; //details of the produts included in the agreement wiht the provider
	
	
	//constructor
	private CommunicationDetails(boolean IsFixedDays, String PhoneNum, String Address, int quantityForDiscount) {
		this.IsFixedDays = IsFixedDays;
		this.PhoneNum  = PhoneNum;
		this.Address = Address;
		this.Agreement = BusinessLayer.Agreement.AgreementCreator(quantityForDiscount);
		this.catalogItems = new LinkedList<CatalogItem>();
	}
	
	private CommunicationDetails(boolean IsFixedDays, String PhoneNum, String Address) {
		this.IsFixedDays = IsFixedDays;
		this.PhoneNum  = PhoneNum;
		this.Address = Address;
		this.Agreement = null;
		this.catalogItems = new LinkedList<CatalogItem>();
	}
	
	//creator
	public static CommunicationDetails communicationDetailsCreatorWithAgreement(boolean IsFixedDays, String PhoneNum, String Address, int quantityForDiscount) {
		CommunicationDetails cd = new CommunicationDetails(IsFixedDays, PhoneNum, Address, quantityForDiscount);
		return cd;
	}
	
	public static CommunicationDetails communicationDetailsCreatorNoAgreement(boolean IsFixedDays, String PhoneNum, String Address) {
		CommunicationDetails cd = new CommunicationDetails(IsFixedDays, PhoneNum, Address);
		return cd;
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

	public int getQuantityForDiscount() {
		if (isAgreement())
			return Agreement.getQuantityForDiscount();
		else
			return 0;
	}

	public void setQuantityForDiscount(int quantityForDiscount) {
		if (isAgreement())
			Agreement.setQuantityForDiscount(quantityForDiscount);
		else
			this.Agreement = BusinessLayer.Agreement.AgreementCreator(quantityForDiscount);
	}
	
	public boolean getIsFixedFays() {
		return IsFixedDays;
	}
	
	
	//methods
	private boolean isAgreement() {
		return (this.Agreement != null);
	}
	
	public static boolean editDetails(CommunicationDetails cd, boolean ssFixedDays, String phoneNum, String address, int quantityForDiscount) {
		cd.setIsFixedDays(ssFixedDays);	
		cd.setPhoneNum(phoneNum);
		cd.setAddress(address);
		cd.setQuantityForDiscount(quantityForDiscount);
		return true;
	}
	
	
	public static String printDetails(CommunicationDetails cd) {
		String output = "Phone: "+cd.PhoneNum + "\n";
		output += "Address: " + cd.Address + "\n";
		if (cd.isAgreement())
			output += "Amount of items needed for discount: " + cd.getQuantityForDiscount();
		return output;
	}
	
	public static String printItems(CommunicationDetails cd) {
		String s = "";
		for(CatalogItem cde : cd.catalogItems)
		s += CatalogItem.printItem(cde)+"\n";
		return s;
	}
	
	public static CatalogItem getItemByID(CommunicationDetails cd, String CatalogItemID) {
		for(CatalogItem c : cd.catalogItems)
			if(c.getCatalogNum().equals(CatalogItemID))
				return c;
		return null;
	}
	
	public static boolean addCatalogItem(CommunicationDetails cd, CatalogItem c) {
		return cd.catalogItems.add(c);
	}

}
