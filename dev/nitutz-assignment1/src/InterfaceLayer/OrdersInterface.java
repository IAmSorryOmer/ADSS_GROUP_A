package InterfaceLayer;


import BusinessLayer.AllOrders;
import BusinessLayer.AllProviders;
import BusinessLayer.CatalogItem;
import BusinessLayer.CommunicationDetails;
import BusinessLayer.Provider;
import BusinessLayer.SingleProviderOrder;
import BusinessLayer.Provider;
public class OrdersInterface {
	
	public SingleProviderOrder SingleProviderOrderCreator (String ID) {
		try {
			AllProviders ap = AllProviders.getInstance();
			Provider p = (AllProviders.getProvidersByID(ap, ID));
			SingleProviderOrder spo = SingleProviderOrder.SingleProviderOrderCreator(p);
			return spo;
			}
			catch(Exception e) {
			return null;
			}
	}
	
	public static boolean AddToOrder (String ID, String ItemID, int orderAmount) {
		AllOrders ao = AllOrders.getInstance();
		AllProviders ap = AllProviders.getInstance();
		Provider p = (AllProviders.getProvidersByID(ap, ID));
		SingleProviderOrder sop = AllOrders.getOrdersFromProvider(ao, p);
		CatalogItem ci = CommunicationDetails.getItemByID(p.getCommunicationDetails(),ItemID);
		if (sop == null | ci == null)
			return false;
		return SingleProviderOrder.AddToOrder(sop, ci, orderAmount);
	}
	
	public static boolean EditOrder (String ID, String ItemID, int orderAmount) {
		AllOrders ao = AllOrders.getInstance();
		AllProviders ap = AllProviders.getInstance();
		Provider p = (AllProviders.getProvidersByID(ap, ID));
		SingleProviderOrder sop = AllOrders.getOrdersFromProvider(ao, p);
		CatalogItem catalogItem = CommunicationDetails.getItemByID(p.getCommunicationDetails(),ItemID);
		if (sop == null | catalogItem == null)
			return false;
		return SingleProviderOrder.EditOrder(sop, catalogItem, orderAmount);
	}
	
	public static boolean RemoveFromOrder (String ID, String ItemID) {
		AllOrders ao = AllOrders.getInstance();
		AllProviders ap = AllProviders.getInstance();
		Provider p = (AllProviders.getProvidersByID(ap, ID));
		SingleProviderOrder sop = AllOrders.getOrdersFromProvider(ao, p);
		CatalogItem catalogItem = CommunicationDetails.getItemByID(p.getCommunicationDetails(),ItemID);
		if (sop == null | catalogItem == null)
			return false;
		return SingleProviderOrder.RemoveFromOrder(sop, catalogItem);
	}
	public static String printItems(String ID) {
		AllOrders ao = AllOrders.getInstance();
		AllProviders ap = AllProviders.getInstance();
		Provider p = (AllProviders.getProvidersByID(ap, ID));
		SingleProviderOrder sop = AllOrders.getOrdersFromProvider(ao, p);
		if  (sop == null)
			return "no order found";
		String s = SingleProviderOrder.printItems(sop);
		return s;
	}
}
