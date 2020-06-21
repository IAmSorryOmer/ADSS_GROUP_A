package BL;

import com.company.DataAccessLayer.CatalogItemDAL;
import com.company.DataAccessLayer.ProviderDAL;
import com.company.Entities.*;

import java.util.LinkedList;
import java.util.List;

public class ProviderController {

	//creator
	public static void ProviderCreator(Provider provider, CommunicationDetails communicationDetails) {
		if(getProvierByID(provider.getProviderID()) != null){
			throw new IllegalArgumentException("there is already provider with id " + provider.getProviderID());
		}
		provider.setCommunicationDetails(communicationDetails);
		ProviderDAL.insertProvider(provider);
	}
	
	
	//methods
	public static void editDetails(String providerId,Boolean NeedsTransport, Integer DelayDays, Integer ArrivalDays, String Name, Boolean isFixedDays, String phoneNum, String address) {
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		if(NeedsTransport != null) {
			provider.setNeedsTransport(NeedsTransport);
		}
		if(DelayDays != null) {
			provider.setDelayDays(DelayDays);
		}
		if(ArrivalDays != null) {
			provider.setArrivalDays(ArrivalDays);
		}
		if(Name != null) {
			provider.setName(Name);
		}
		CommunicationDetailsController.editDetails(provider, isFixedDays, phoneNum, address);
		ProviderDAL.updateProvider(provider);
	}
	
	/*
	 * @return returns provider by id, or null if no such provider exists
	 */
	public static Provider getProvierByID (String providerID) {
		return ProviderDAL.getProviderById(providerID);
	}


	public static void addCatalogItem(String providerId, CatalogItem catalogItem, String productDetailsId) {
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		ProductDetails productDetails = ProductDetailsController.getProductDetailsById(productDetailsId);
		if(productDetails == null){
			throw new IllegalArgumentException("there is no product details with id " + productDetailsId + ".");
		}
		CommunicationDetailsController.addCatalogItem(provider.getCommunicationDetails(), catalogItem);
		CatalogItemController.CatalogItemCreator(provider, catalogItem, productDetails);
	}

	public static void editCatalogItem (String providerId, String catalogItemId, double price) {
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		CatalogItemController.editCatalogItem(provider, catalogItemId, price);
	}

	public static void removeCatalogItem (String providerId, String catalogItemId) {
		Provider provider = getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		CatalogItemController.removeItem(provider, catalogItemId);
	}

	public static boolean isWorkingAtDay(int day, Provider provider){
		return (provider.getArrivalDays() & day) != 0;
	}

	private static String getArrivalDays(Provider p){
		String arr[] = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		String result = "";
		for(int i = 0; i< 7;i++){
			if(isWorkingAtDay(1 << i, p))
				result += arr[i] + ", ";
		}
		if(result.length() != 0)
			result = result.substring(0,result.length()-2);
		return result;
	}
	public static List<CatalogItem> getAllProviderItems(String providerId){
		Provider p = ProviderController.getProvierByID(providerId);
		if(p == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		return CommunicationDetailsController.getProviderItems(p);
	}


	public static String printDetails(String providerId) {
		Provider p = ProviderController.getProvierByID(providerId);
		if(p == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		String output = "provider details:\n";
		output += "ID: " + providerId + "\n";
		output += "CreditCardNum: " + p.getCreditCardNumber() + "\n";
		output += "need transport: "+p.isNeedsTransport() + "\n";
		if(!p.getCommunicationDetails().getIsFixedDays())
			output += "Delay Untill Delivery: " + p.getDelayDays() + " Days\n";
		else {
			output += "Comes in days: " + getArrivalDays(p) + "\n";
		}
		output += "Name: "+p.getName() + "\n";
		output += CommunicationDetailsController.printDetails(p.getCommunicationDetails())+"\n";
		return output;
	}

	public static Pair<Provider, CatalogItem> getBestProviderForProduct(ProductDetails productDetails, int amount){
		Provider minProvider = null;
		CatalogItem minItem = null;
		double minPrice = 0;
		List<Provider> providerList = ProviderDAL.loadAll();//must first load all providers
		for(Provider provider : providerList){
			CatalogItem providerItem = CommunicationDetailsController.getItemByProductDetails(provider.getCommunicationDetails(), productDetails.getId());
			if(providerItem != null){
				double providerPrice = SingleProviderOrderController.calcItemCategoryPrice(provider, providerItem, amount);
				if(minProvider == null || providerPrice < minPrice){
					minProvider = provider;
					minItem = providerItem;
					minPrice = providerPrice;
				}
			}
		}
		return new Pair<>(minProvider, minItem);
	}
	public static List<Provider> getAllProviders(){
		return ProviderDAL.loadAll();
	}
}
