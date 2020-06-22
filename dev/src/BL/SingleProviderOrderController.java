package BL;

import DAL.OrdersDAL;
import Entities.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class SingleProviderOrderController {


	//creators
	public static void SingleProviderOrderCreator (SingleProviderOrder singleProviderOrder, String providerId) {
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		createWithProviderObj(singleProviderOrder, provider);
	}

	public static void createWithProviderObj(SingleProviderOrder singleProviderOrder, Provider provider){
		if(getOrderById(singleProviderOrder.getOrderID()) != null){
			throw new IllegalArgumentException("there is already order with id " + singleProviderOrder.getOrderID());
		}
		singleProviderOrder.setProvider(provider);

		OrdersDAL.insertOrder(singleProviderOrder);
		if(singleProviderOrder.isAutomatic()){
			handleAutomaticOrder(singleProviderOrder);
		}
	}

	private static void handleAutomaticOrder(SingleProviderOrder automaticOrder){
		//TODO insert here automatic order logic
	}

	/*public static LocalDate getNextAutoOrderDate(AutomaticOrder automaticOrder){
		LocalDate minDate = null;
		for(int i = 0; i < 7 ;i++){
			if(automaticOrder.isComingAtDay(i)){
				int day = ((i-1) % 7) + 1;
				LocalDate nextDayDate = LocalDate.now().plusDays(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(day)));
				if(minDate == null || minDate.compareTo(nextDayDate) > 0){
					minDate = nextDayDate;
				}
			}
		}
		return minDate;
	}*/

	//methods
	public  static void AddToOrder (String providerId, String orderId, int storeId, String ItemID, int orderAmount) {
		if (orderAmount < 1)
			throw new IllegalArgumentException("order amount must be positive");
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		SingleProviderOrder singleProviderOrder = getOrderById(orderId);
		if(singleProviderOrder == null || !singleProviderOrder.getProvider().getProviderID().equals(providerId) || singleProviderOrder.getStoreId() != storeId){
			throw new IllegalArgumentException("there is no order with id " + orderId + " for provider with id "+provider.getProviderID() + " and store number " + storeId);
		}
		//TODO what the meaning of this now?
		ensureTimes(singleProviderOrder);
		CatalogItem catalogItem = CatalogItemController.getCatalogItemById(ItemID);
		if(catalogItem == null || !catalogItem.getProviderID().equals(providerId)){
			throw new IllegalArgumentException("there is no item with id " + ItemID + " for provider with id " + providerId);
		}
		else if(singleProviderOrder.isItemExist(catalogItem)){
			throw new IllegalArgumentException("item with id " + catalogItem.getCatalogNum() + " is already in the order.");
		}
		singleProviderOrder.addToItemList(catalogItem, orderAmount);
		OrdersDAL.insertItemToOrder(singleProviderOrder, catalogItem, orderAmount);
	}
		
	public static void EditOrder (String providerId, String orderId, int storeId, String ItemID, int orderAmount) {
		if (orderAmount < 1)
			throw new IllegalArgumentException("order amount must be positive");
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		SingleProviderOrder singleProviderOrder = getOrderById(orderId);
		if(singleProviderOrder == null || !singleProviderOrder.getProvider().getProviderID().equals(providerId) || singleProviderOrder.getStoreId() != storeId){
			throw new IllegalArgumentException("there is no order with id " + orderId + " for provider with id "+provider.getProviderID() + " and store number " + storeId);
		}
		ensureTimes(singleProviderOrder);
		CatalogItem catalogItem = CatalogItemController.getCatalogItemById(ItemID);
		if(catalogItem == null || !catalogItem.getProviderID().equals(providerId)){
			throw new IllegalArgumentException("there is no item with id " + ItemID + " for provider with id " + providerId);
		}
		else if(!singleProviderOrder.isItemExist(catalogItem)){
			throw new IllegalArgumentException("item with id " + catalogItem.getCatalogNum() + " isnt in the order.");
		}
		singleProviderOrder.editItemList(catalogItem, orderAmount);
		OrdersDAL.editItemOnOrder(singleProviderOrder, catalogItem, orderAmount);
	}
		
	public static void RemoveFromOrder (String providerId, String orderId, int storeId, String ItemID) {
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		SingleProviderOrder singleProviderOrder = getOrderById(orderId);
		if(singleProviderOrder == null || !singleProviderOrder.getProvider().getProviderID().equals(providerId) || singleProviderOrder.getStoreId() != storeId){
			throw new IllegalArgumentException("there is no order with id " + orderId + " for provider with id "+provider.getProviderID() + " and store number " + storeId);
		}
		ensureTimes(singleProviderOrder);
		CatalogItem catalogItem = CatalogItemController.getCatalogItemById(ItemID);
		if(catalogItem == null || !catalogItem.getProviderID().equals(providerId)){
			throw new IllegalArgumentException("there is no item with id " + ItemID + " for provider with id " + providerId);
		}
		else if(!singleProviderOrder.isItemExist(catalogItem)){
			throw new IllegalArgumentException("item with id " + catalogItem.getCatalogNum() + " isnt already in the order.");
		}
		singleProviderOrder.removeFromItemList(catalogItem);
		OrdersDAL.removeItemFromOrder(singleProviderOrder, catalogItem);
	}
	private static void ensureTimes(SingleProviderOrder singleProviderOrder){
		if(singleProviderOrder.isAutomatic()){
			//TODO replace logic here
			LocalDate nextDate = null; //TODO here need to decide when is the next date. getNextAutoOrderDate((AutomaticOrder)singleProviderOrder);
			long hoursUntil = LocalDateTime.now().until(nextDate, ChronoUnit.HOURS);
			if(hoursUntil <= 24){
				throw new IllegalArgumentException("you cant edit automatic order less than a day before order");
			}
		}
	}
	public static SingleProviderOrder getOrderById(String id){
		return OrdersDAL.getOrderById(id);
	}

	public static double calcItemCategoryPrice (Provider provider, CatalogItem catalogItem, int amountOrdered) {
		double itemSum = amountOrdered * catalogItem.getPrice();
		Agreement agreement = AgreementController.getProviderAgreement(provider);
		if(agreement != null && agreement.doesDiscountExist(catalogItem)){
			int minAmount = agreement.getItemQuantityForDiscount(catalogItem);
			if(amountOrdered >= minAmount){
				double percent = agreement.getItemDiscountPercent(catalogItem);
				itemSum = itemSum * ((100-percent)/100);
			}
		}
		return itemSum;
	}

	public static double calculateOrderPrice(SingleProviderOrder singleProviderOrder){
		double sum = 0;
		Agreement agreement = AgreementController.getProviderAgreement(singleProviderOrder.getProvider());
		for(Map.Entry<CatalogItem, Integer> entry : singleProviderOrder.getOrderItems().entrySet()){
			double itemSum = entry.getValue() * entry.getKey().getPrice();
			if(agreement != null && agreement.doesDiscountExist(entry.getKey())){
				int minAmount = agreement.getItemQuantityForDiscount(entry.getKey());
				if(entry.getValue() >= minAmount){
					double percent = agreement.getItemDiscountPercent(entry.getKey());
					itemSum = itemSum * ((100-percent)/100);
				}
			}
			sum += itemSum;
		}
		return sum;
	}

	public static String printOrder(String providerId, String orderId, int storeId) {
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		SingleProviderOrder singleProviderOrder = getOrderById(orderId);
		if(singleProviderOrder == null || !singleProviderOrder.getProvider().getProviderID().equals(providerId) || singleProviderOrder.getStoreId() != storeId){
			throw new IllegalArgumentException("there is no order with id " + orderId + " for provider with id "+provider.getProviderID() + " and store number " + storeId);
		}
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(singleProviderOrder.toString());
		for(Map.Entry<CatalogItem, Integer> entry : singleProviderOrder.getOrderItems().entrySet()) {
			CatalogItem catalogItem = entry.getKey();
			double originalPrice = entry.getValue() * catalogItem.getPrice();
			double finalPrice = calcItemCategoryPrice(provider, catalogItem, entry.getValue());
			double discount = (1 - (finalPrice/originalPrice)) * 100;
			toReturn.append("Item Id: ").append(catalogItem.getCatalogNum()).append(", Item Name: ").append(catalogItem.GetDescribedProductName()).append(", amount: ").
					append(" ").append(entry.getValue()).append("Original Price: ").append(originalPrice).append(", discount: ").append(discount).append("%, final price: ").append(finalPrice).append(".\n");
		}
		return toReturn.toString();
	}

	public static void autoOrderListOfProductsInStore(int storeId, List<ProductDetails> productDetailsToOrder){
		HashMap<Provider, SingleProviderOrder> orders = new HashMap<>();
		for(ProductDetails productDetails : productDetailsToOrder){
			int amountToOrder = (productDetails.getMinimumQuantity() - ProductDetailsController.getProductDetailsQuantityInStore(storeId, productDetails.getId())) + 10;
			if(amountToOrder <= 0){
				continue;
			}
			Pair<Provider, CatalogItem> minProviderPair = ProviderController.getBestProviderForProduct(productDetails, amountToOrder);
			if(minProviderPair.getFirst() == null){
				//TODO there is no provider which provides the item. maybe print something
			}
			else{
				if(!orders.containsKey(minProviderPair.getFirst())){
					SingleProviderOrder orderToAdd = new SingleProviderOrder(minProviderPair.getFirst(), storeId, UUID.randomUUID().toString(), LocalDate.now(), 0);
					orders.put(minProviderPair.getFirst(), orderToAdd);
				}
				orders.get(minProviderPair.getFirst()).addToItemList(minProviderPair.getSecond(), amountToOrder);
			}
		}
		for(Map.Entry<Provider, SingleProviderOrder> entry : orders.entrySet()){
			SingleProviderOrderController.createWithProviderObj(entry.getValue(), entry.getKey());
		}
	}
	public static List<SingleProviderOrder> getAllStoreOrdersOfProvider(int storeId, String providerId){
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		return OrdersDAL.getStoreOrdersOfProvider(storeId, providerId);
	}
	public static List<SingleProviderOrder> getAllAutomaticOrdersToSupply(LocalDate date){
		return OrdersDAL.getOrdersToSupply(date);
	}
	public static List<SingleProviderOrder> getAllStoreOrders(int storeId){
		return OrdersDAL.getOrdersOfStore(storeId);
	}
	public static List<SingleProviderOrder> getAllStoreAutomaticsOrders(int storeId){
		return OrdersDAL.getAutomaticOrdersOfStore(storeId);
	}
}
