package InterfaceLayer;

import BusinessLayer.AllOrders;
import BusinessLayer.AllProviders;
import BusinessLayer.CatalogItem;
import BusinessLayer.CommunicationDetails;
import BusinessLayer.Provider;
import BusinessLayer.SingleProviderOrder;

public class OrdersInterface {
	public SingleProviderOrder SingleProviderOrderCreator (String ID) {
		try {
			AllProviders ap = AllProviders.getInstance();
			Provider p = (AllProviders.getProvidersByID(ap, ID).get(0));
			SingleProviderOrder spo = SingleProviderOrder.SingleProviderOrderCreator(p);
			return spo;
			}
			catch(Exception e) {
			return null;
			}
	}
	public static boolean AddToOrder (String ID, CatalogItem catalogItem, int orderAmount) {
		try {
			AllOrders ao = AllOrders.getInstance();
			AllProviders ap = AllProviders.getInstance();
			Provider p = (AllProviders.getProvidersByID(ap, ID).get(0));
			SingleProviderOrder sop = AllOrders.getOrdersFromProvider(ao, p).get(0);
			return SingleProviderOrder.AddToOrder(sop, catalogItem, orderAmount);
		}
		catch(Exception e) {
			return false;
		}
	}
	public static boolean EditOrder (String ID, CatalogItem catalogItem, int orderAmount) {
		try {
			AllOrders ao = AllOrders.getInstance();
			AllProviders ap = AllProviders.getInstance();
			Provider p = (AllProviders.getProvidersByID(ap, ID).get(0));
			SingleProviderOrder sop = AllOrders.getOrdersFromProvider(ao, p).get(0);
			return SingleProviderOrder.EditOrder(sop, catalogItem, orderAmount);
		}
		catch(Exception e) {
			return false;
		}
	}
	public static boolean RemoveFromOrder (String ID, CatalogItem catalogItem) {
		try {
			AllOrders ao = AllOrders.getInstance();
			AllProviders ap = AllProviders.getInstance();
			Provider p = (AllProviders.getProvidersByID(ap, ID).get(0));
			SingleProviderOrder sop = AllOrders.getOrdersFromProvider(ao, p).get(0);
			return SingleProviderOrder.RemoveFromOrder(sop, catalogItem);
		}
		catch(Exception e) {
			return false;
		}
	}
}
