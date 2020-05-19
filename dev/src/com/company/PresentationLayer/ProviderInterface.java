package com.company.PresentationLayer;

import com.company.Entities.CatalogItem;
import com.company.Entities.CommunicationDetails;
import com.company.Entities.Provider;
import com.company.LogicLayer.AgreementController;
import com.company.LogicLayer.CatalogItemController;
import com.company.LogicLayer.ProviderController;
import com.sun.org.apache.xalan.internal.xsltc.dom.AdaptiveResultTreeImpl;

import java.util.List;


public class ProviderInterface {

	//Provider
	public static void ProviderCreator(Provider provider, CommunicationDetails communicationDetails) {
		ProviderController.ProviderCreator(provider, communicationDetails);
	}
	public static void editDetails(String providerId,Boolean NeedsTransport, Integer DelayDays, Integer ArrivalDays, String Name, Boolean isFixedDays, String phoneNum, String address) {
		ProviderController.editDetails(providerId,NeedsTransport,DelayDays,ArrivalDays,Name,isFixedDays,phoneNum, address);
	}
	public static String printDetails(String ID) {
		return ProviderController.printDetails(ID);
	}

	public static List<Provider> getAllProviders(){
		return ProviderController.getAllProviders();
	}
}
