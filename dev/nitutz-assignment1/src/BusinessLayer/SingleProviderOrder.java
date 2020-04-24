package BusinessLayer;

import java.util.HashMap;
import java.util.Map;


public class SingleProviderOrder{
	
	//fields
	private String OrderID;
	private Provider Provider;
	private Map<CatalogItem, Pair<Integer, Double>> ItemList_amount_price; //catalog item - amount of item - price each
	private int totalItemAmount;
	
	//consructors
	private SingleProviderOrder (Provider provider) {
		this.OrderID = provider.getProviderID();
		this.Provider = provider;
		this.ItemList_amount_price = new HashMap<CatalogItem, Pair<Integer, Double>>();
		this.totalItemAmount = 0;
	}
	
	//creators
	public static SingleProviderOrder SingleProviderOrderCreator (Provider provider) {
		SingleProviderOrder spo = new SingleProviderOrder(provider);
		AllOrders.getInstance().addOrder(spo);
		return spo;
	}
	
	//methods
	public static boolean AddToOrder (SingleProviderOrder sop, CatalogItem catalogItem, int orderAmount) {
		if (orderAmount < 1)
			return false;
		sop.addToItemList(catalogItem, orderAmount);
		sop.setTotalItemAmount(sop.getTotalItemAmount() + orderAmount);
		sop.setprices();
		return true;
	}
	
	public static boolean EditOrder (SingleProviderOrder sop, CatalogItem catalogItem, int orderAmount) {
		if (orderAmount < 0)
			return false;
		else if (orderAmount == 0)
			return RemoveFromOrder(sop, catalogItem);
		sop.editItemList(catalogItem, orderAmount);
		sop.setTotalItemAmount((orderAmount - sop.getSpecificItemAmount(catalogItem)) + sop.getTotalItemAmount());
		sop.setprices();
		return true;
	}
	
	public static boolean RemoveFromOrder (SingleProviderOrder sop, CatalogItem catalogItem) {
		if (!sop.ItemList_amount_price.containsKey(catalogItem))
			return false;
		int oldAmount =  sop.getSpecificItemAmount(catalogItem);
		sop.removeFromItemList(catalogItem);
		sop.setTotalItemAmount(sop.getTotalItemAmount() - oldAmount);
		sop.setprices();
		return true;
	}
	
	private void setprices() {
		for (Map.Entry<CatalogItem, Pair<Integer, Double>> mapEntry : ItemList_amount_price.entrySet()) {
			int itemAmount = mapEntry.getValue().getFirst();
			int quantityForDiscount = Provider.getCommunicationDetails().getQuantityForDiscount();
			double itemFinalPrice = mapEntry.getKey().calculateFinalPrice(itemAmount, totalItemAmount, quantityForDiscount);
			mapEntry.getValue().setSecond(itemFinalPrice);
		}
	}
	
	//getters  & setters
	public void addToItemList(CatalogItem catalogItem, int orderAmount) {
		ItemList_amount_price.put(catalogItem, new Pair<Integer,Double>(new Integer(orderAmount),new Double(0)));
	}
	
	public void editItemList(CatalogItem catalogItem, int orderAmount) {
		ItemList_amount_price.replace(catalogItem, new Pair<Integer, Double>(new Integer(orderAmount),new Double(0)));
	}
	
	public void removeFromItemList(CatalogItem catalogItem) {
		ItemList_amount_price.remove(catalogItem);
	}

	public String getOrderID() {
		return OrderID;
	}

	public Provider getProvider() {
		return Provider;
	}
	
	public int getTotalItemAmount() {
		return totalItemAmount;
	}

	public void setTotalItemAmount(int totalItemAmount) {
		this.totalItemAmount = totalItemAmount;
	}
	
	public int getSpecificItemAmount (CatalogItem catalogItem) {
		return ItemList_amount_price.get(catalogItem).getFirst();
	}
public static String printItems(SingleProviderOrder spo) {	
	String s = "";
	for(CatalogItem cde : spo.ItemList_amount_price.keySet())
	s += CatalogItem.printItem(cde)+"\n";
	return s;
	}
}
