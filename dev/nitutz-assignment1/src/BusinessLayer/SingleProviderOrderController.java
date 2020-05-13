package BusinessLayer;

import java.util.List;
import java.util.Map;

public class SingleProviderOrderController {

	public static List<SingleProviderOrder> singleProviderOrderList;
	
	
	//creators
	public static SingleProviderOrder SingleProviderOrderCreator (Provider provider) {
		SingleProviderOrder spo = new SingleProviderOrder(provider);
		singleProviderOrderList.add(spo);
		return spo;
	}
	
	
	//methods
	public static boolean AddToOrder (SingleProviderOrder spo, CatalogItem catalogItem, int orderAmount) {
		if (orderAmount < 1)
			return false;
		double itemTotalPrice = SingleProviderOrderController.calcItemCategoryPrice(spo.getProvider(), catalogItem, orderAmount);
		spo.addToItemList(catalogItem, orderAmount, itemTotalPrice);
		spo.setTotalItemAmount(spo.getTotalItemAmount() + orderAmount);
		spo.setFinalPrice(spo.getFinalPrice() + itemTotalPrice);
		return true;
	}
		
	public static boolean EditOrder (SingleProviderOrder spo, CatalogItem catalogItem, int newOrderAmount) {
		if (newOrderAmount < 0)
			return false;
		else if (newOrderAmount == 0)
			return RemoveFromOrder(spo, catalogItem);
		double oldTotalPrice = spo.getItemTotalPrice(catalogItem);
		double newItemTotalPrice = SingleProviderOrderController.calcItemCategoryPrice(spo.getProvider(), catalogItem, newOrderAmount);
		spo.editItemList(catalogItem, newOrderAmount, newItemTotalPrice);
		spo.setFinalPrice((newItemTotalPrice-oldTotalPrice) + spo.getFinalPrice());
		spo.setTotalItemAmount((newOrderAmount - spo.getSpecificItemAmount(catalogItem)) + spo.getTotalItemAmount());
		return true;
	}
		
	public static boolean RemoveFromOrder (SingleProviderOrder spo, CatalogItem catalogItem) {
		double totalItemPrice = spo.getItemTotalPrice(catalogItem);
		spo.setTotalItemAmount(spo.getTotalItemAmount() - spo.getSpecificItemAmount(catalogItem));
		spo.setFinalPrice(spo.getFinalPrice() - totalItemPrice);
		spo.removeFromItemList(catalogItem);
		return true;
	}
		
		
	public static double calcItemCategoryPrice (Provider provier, CatalogItem catalogItem, int amountOrdered) {
		double percentDiscount = 0;
		int amountOfDiscounts = 0;
		Agreement agreement = provier.getCommunicationDetails().getAgreement();
		if (agreement != null) {
			amountOfDiscounts = amountOrdered % agreement.getItemQuantityForDiscount(catalogItem);
			percentDiscount = agreement.getItemDiscountPercent(catalogItem);
		}
		return catalogItem.getPrice() * amountOrdered * Math.pow(1-(percentDiscount/100),(amountOfDiscounts));
	}
		
	public static String printOrder(SingleProviderOrder spo) {	
	String s = "OrderID: " + spo.getOrderID() + "\n";
	s += "Item list\n";
	for(CatalogItem catalogItem : spo.getItemList_amount_price().keySet()) {
		s += CatalogItemController.printItem(catalogItem)+"\n";
		s += "amount: " + spo.getSpecificItemAmount(catalogItem);
	}	
	s += "total items ordered: " + spo.getTotalItemAmount() + "\n";
	s += "final price: " + spo.getFinalPrice();
	return s;
	}
}
