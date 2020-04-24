package InterfaceLayer;


import BusinessLayer.AllOrders;
import BusinessLayer.AllProviders;
import BusinessLayer.CatalogItem;
import BusinessLayer.CommunicationDetails;
import BusinessLayer.Provider;
import BusinessLayer.SingleProviderOrder;
import sun.security.jca.GetInstance;
import BusinessLayer.Provider;
public class OrdersInterface {
	
	public SingleProviderOrder SingleProviderOrderCreator (String ID) {
		try {
			Provider p = (AllProviders.getInstance().getProvidersByID(ID));
			SingleProviderOrder spo = SingleProviderOrder.SingleProviderOrderCreator(p);
			return spo;
			}
			catch(Exception e) {
			return null;
			}
	}
	
	public static boolean AddToOrder (String ID, String ItemID, int orderAmount) {
		Provider p = (AllProviders.getInstance().getProvidersByID(ID));
		SingleProviderOrder sop = AllOrders.getInstance().getOrdersFromProvider(p);
		CatalogItem ci = CommunicationDetails.getItemByID(p.getCommunicationDetails(),ItemID);
		if (sop == null | ci == null)
			return false;
		return SingleProviderOrder.AddToOrder(sop, ci, orderAmount);
	}
	
	public static boolean EditOrder (String ID, String ItemID, int orderAmount) {
		Provider p = (AllProviders.getInstance().getProvidersByID(ID));
		SingleProviderOrder sop = AllOrders.getInstance().getOrdersFromProvider(p);
		CatalogItem catalogItem = CommunicationDetails.getItemByID(p.getCommunicationDetails(),ItemID);
		if (sop == null | catalogItem == null)
			return false;
		return SingleProviderOrder.EditOrder(sop, catalogItem, orderAmount);
	}
	
	public static boolean RemoveFromOrder (String ID, String ItemID) {
		Provider p = (AllProviders.getInstance().getProvidersByID(ID));
		SingleProviderOrder sop = AllOrders.getInstance().getOrdersFromProvider(p);
		CatalogItem catalogItem = CommunicationDetails.getItemByID(p.getCommunicationDetails(),ItemID);
		if (sop == null | catalogItem == null)
			return false;
		return SingleProviderOrder.RemoveFromOrder(sop, catalogItem);
	}
	public static String printItems(String ID) {
		Provider p = (AllProviders.getInstance().getProvidersByID(ID));
		SingleProviderOrder sop = AllOrders.getInstance().getOrdersFromProvider(p);
		if  (sop == null)
			return "no order found";
		String s = SingleProviderOrder.printItems(sop);
		return s;
	}
}
