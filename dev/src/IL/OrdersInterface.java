package IL;


import Entities.*;
import BL.SingleProviderOrderController;

import java.util.List;

public class OrdersInterface {
	
	public static void SingleProviderOrderCreator (SingleProviderOrder singleProviderOrder, String providerId) {
		SingleProviderOrderController.SingleProviderOrderCreator(singleProviderOrder, providerId);
	}
	
	public static void AddToOrder(String providerId, String orderId, String ItemID, int orderAmount) {
		SingleProviderOrderController.AddToOrder(providerId, orderId, ItemID, orderAmount);
	}
	
	public static void EditOrder (String providerId, String orderId, String ItemID, int orderAmount) {
		SingleProviderOrderController.EditOrder(providerId,orderId, ItemID, orderAmount);
	}
	
	public static void RemoveFromOrder (String providerId, String orderId, String ItemID) {
		SingleProviderOrderController.RemoveFromOrder(providerId, orderId, ItemID);
	}

	public static String printOrderExpanded(String providerId, String orderId) {
		return SingleProviderOrderController.printOrder(providerId, orderId);
	}

	public static List<SingleProviderOrder> getAllProviderOrders(String providerId){
		return SingleProviderOrderController.getAllOrdersOfProvider(providerId);
	}

	public static List<SingleProviderOrder> getAllOrders(){
		return SingleProviderOrderController.getAllOrders();
	}
	public static List<SingleProviderOrder> getAllAutomaticsOrders(){
		return SingleProviderOrderController.getAllAutomaticsOrders();
	}
}
