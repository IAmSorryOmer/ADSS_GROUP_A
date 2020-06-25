package IL;


import BL.ProviderController;
import DAL.OrdersDAL;
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
	public static void rescheduleOrder(String orderId, int storeId){
		SingleProviderOrderController.rescheduleOrderDelivery(orderId, storeId);
	}

	public static String printOrderExpanded(String providerId, String orderId, int storeId) {
		return SingleProviderOrderController.printOrder(providerId, orderId, storeId);
	}
	public static void acceptOrder(String orderId, int storeId){
		SingleProviderOrderController.acceptOrder(orderId, storeId);
	}
	public static void modifyItemsBeforeAccept(String orderId, int storeId, List<Pair<String, Integer>> items){
		SingleProviderOrderController.modifyAmountsBeforeAccept(orderId, storeId, items);
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

	public static List<SingleProviderOrder> getAllStoreOrdersOfProvider(int storeId, String providerId){
		return SingleProviderOrderController.getAllStoreOrdersOfProvider(storeId, providerId);
	}

	public static List<SingleProviderOrder> getAllStoreOrders(int storeId){
		return SingleProviderOrderController.getAllStoreOrders(storeId);
	}

	public static List<SingleProviderOrder> getNotScheduledOrders(int storeId){
		return SingleProviderOrderController.getNotScheduledOrders(storeId);
	}

	public static List<SingleProviderOrder> getNotShippedOrders(int storeId, LocalDate date){
		return SingleProviderOrderController.getNotShippedOrders(storeId, date);
	}

	public static List<SingleProviderOrder> getNotHandledOrders(int storeId){
		return SingleProviderOrderController.getNotHandledOrders(storeId);
	}
	public static List<SingleProviderOrder> getAllProviderTransportedDeliveries(int storeId){
		return SingleProviderOrderController.getAllProviderTransportedDeliveries(storeId);
	}
	public static List<SingleProviderOrder> getAllStoreAutomaticsOrders(int storeId){
		return SingleProviderOrderController.getAllStoreAutomaticsOrders(storeId);
	}
	public static String stringifyOrderItemsToHandle(int storeId, String orderId){
		return SingleProviderOrderController.printOrderToHandle(orderId, storeId);
	}

}
