package IL;


import Entities.*;
import BL.SingleProviderOrderController;

import java.time.LocalDate;
import java.util.List;

public class OrdersInterface {
	
	public static void SingleProviderOrderCreator (SingleProviderOrder singleProviderOrder, String providerId) {
		SingleProviderOrderController.SingleProviderOrderCreator(singleProviderOrder, providerId);
	}
	
	public static void AddToOrder(String providerId, String orderId, int storeId, String ItemID, int orderAmount) {
		SingleProviderOrderController.AddToOrder(providerId, orderId, storeId, ItemID, orderAmount);
	}
	
	public static void EditOrder (String providerId, String orderId, int storeId, String ItemID, int orderAmount) {
		SingleProviderOrderController.EditOrder(providerId,orderId, storeId, ItemID, orderAmount);
	}
	
	public static void RemoveFromOrder (String providerId, String orderId, int storeId, String ItemID) {
		SingleProviderOrderController.RemoveFromOrder(providerId, orderId, storeId, ItemID);
	}

	public static String printOrderExpanded(String providerId, String orderId, int storeId) {
		return SingleProviderOrderController.printOrder(providerId, orderId, storeId);
	}

	public static List<SingleProviderOrder> getAllStoreProviderOrders(int storeId, String providerId){
		return SingleProviderOrderController.getAllStoreOrdersOfProvider(storeId, providerId);
	}

	public static List<SingleProviderOrder> getAllOrdersOfStore(int storeId){
		return SingleProviderOrderController.getAllStoreOrders(storeId);
	}
	public static List<SingleProviderOrder> getAllAutomaticsOrdersOfStore(int storeId){
		return SingleProviderOrderController.getAllStoreAutomaticsOrders(storeId);
	}

	public static List<SingleProviderOrder> getAllAutomaticOrdersToSupply(LocalDate localDate){
		return SingleProviderOrderController.getAllAutomaticOrdersToSupply(localDate);
	}
}
