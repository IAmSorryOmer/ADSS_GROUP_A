package com.company.LogicLayer;

import com.company.DataAccessLayer.OrdersDAL;
import com.company.Entities.*;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.junit.runners.model.TestTimedOutException;
import sun.management.Agent;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
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
		if(getOrderByIdAndProvider(singleProviderOrder.getOrderID(), provider) != null){
			throw new IllegalArgumentException("there is already order with id " + singleProviderOrder.getOrderID() + " for provider with id "+provider.getProviderID());
		}
		singleProviderOrder.setProvider(provider);
		OrdersDAL.insertOrder(singleProviderOrder);
		if(singleProviderOrder instanceof AutomaticOrder){
			handleAutomaticOrder((AutomaticOrder)singleProviderOrder, provider);
		}
	}

	private static void handleAutomaticOrder(AutomaticOrder automaticOrder, Provider provider){
		for(int i = 0;i<7;i++){
			if(automaticOrder.isComingAtDay(i) && !provider.isWorkingAtDay(i)){
				throw new IllegalArgumentException("the provider not coming at one of the selected order days");
			}
		}
		LocalDate nextDate = getNextAutoOrderDate(automaticOrder);
		long time = LocalDateTime.now().until(nextDate.atStartOfDay(), ChronoUnit.HOURS);//TODO change to millis
		Timer timer = new Timer();
		System.out.println("scheduling order to " + LocalDateTime.now().plus(time, ChronoUnit.SECONDS).toString());
		timer.schedule(new OrderTask(automaticOrder), time*1000);
		OrdersDAL.insertAutomaticOrder(automaticOrder);
	}

	public static LocalDate getNextAutoOrderDate(AutomaticOrder automaticOrder){
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
	}

	//methods
	public static void AddToOrder (String providerId, String orderId, String ItemID, int orderAmount) {
		if (orderAmount < 1)
			throw new IllegalArgumentException("order amount must be positive");
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		SingleProviderOrder singleProviderOrder = getOrderByIdAndProvider(orderId, provider);
		if(singleProviderOrder == null){
			throw new IllegalArgumentException("there is no order with id " + orderId + " for provider with id "+provider.getProviderID());
		}
		ensureTimes(singleProviderOrder);
		CatalogItem catalogItem = CatalogItemController.getCatalogItemById(provider, ItemID);
		if(catalogItem == null){
			throw new IllegalArgumentException("there is no item with id " + ItemID + " for provider with id " + providerId);
		}
		else if(singleProviderOrder.isItemExist(catalogItem)){
			throw new IllegalArgumentException("item with id " + catalogItem.getCatalogNum() + " is already in the order.");
		}
		singleProviderOrder.addToItemList(catalogItem, orderAmount);
		OrdersDAL.insertItemToOrder(singleProviderOrder, catalogItem, orderAmount);
	}
		
	public static void EditOrder (String providerId, String orderId, String ItemID, int orderAmount) {
		if (orderAmount < 1)
			throw new IllegalArgumentException("order amount must be positive");
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		SingleProviderOrder singleProviderOrder = getOrderByIdAndProvider(orderId, provider);
		if(singleProviderOrder == null){
			throw new IllegalArgumentException("there is no order with id " + orderId + " for provider with id "+provider.getProviderID());
		}
		ensureTimes(singleProviderOrder);
		CatalogItem catalogItem = CatalogItemController.getCatalogItemById(provider, ItemID);
		if(catalogItem == null){
			throw new IllegalArgumentException("there is no item with id " + ItemID + " for provider with id " + providerId);
		}
		else if(!singleProviderOrder.isItemExist(catalogItem)){
			throw new IllegalArgumentException("item with id " + catalogItem.getCatalogNum() + " isnt in the order.");
		}
		singleProviderOrder.editItemList(catalogItem, orderAmount);
		OrdersDAL.editItemOnOrder(singleProviderOrder, catalogItem, orderAmount);
	}
		
	public static void RemoveFromOrder (String providerId, String orderId, String ItemID) {
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		SingleProviderOrder singleProviderOrder = getOrderByIdAndProvider(orderId, provider);
		if(singleProviderOrder == null){
			throw new IllegalArgumentException("there is no order with id " + singleProviderOrder.getOrderID() + " for provider with id "+provider.getProviderID());
		}
		ensureTimes(singleProviderOrder);
		CatalogItem catalogItem = CatalogItemController.getCatalogItemById(provider, ItemID);
		if(catalogItem == null){
			throw new IllegalArgumentException("there is no item with id " + ItemID + " for provider with id " + providerId);
		}
		else if(!singleProviderOrder.isItemExist(catalogItem)){
			throw new IllegalArgumentException("item with id " + catalogItem.getCatalogNum() + " isnt already in the order.");
		}
		singleProviderOrder.removeFromItemList(catalogItem);
		OrdersDAL.removeItemFromOrder(singleProviderOrder, catalogItem);
	}
	private static void ensureTimes(SingleProviderOrder singleProviderOrder){
		if(singleProviderOrder instanceof AutomaticOrder){
			LocalDate nextDate = getNextAutoOrderDate((AutomaticOrder)singleProviderOrder);
			long hoursUntil = LocalDateTime.now().until(nextDate, ChronoUnit.HOURS);
			if(hoursUntil <= 24){
				throw new IllegalArgumentException("you cant edit automatic order less than a day before order");
			}
		}
	}
	public static SingleProviderOrder getOrderByIdAndProvider(String id, Provider provider){
		return OrdersDAL.getOrderById(id, provider.getProviderID());
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

	public static String printOrder(String providerId, String orderId) {
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		SingleProviderOrder singleProviderOrder = getOrderByIdAndProvider(orderId, provider);
		if(singleProviderOrder == null){
			throw new IllegalArgumentException("there is no order with id " + singleProviderOrder.getOrderID() + " for provider with id "+provider.getProviderID());
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

	public static void autoOrderListOfProducts(List<ProductDetails> productDetailsToOrder){
		HashMap<Provider, SingleProviderOrder> orders = new HashMap<>();
		for(ProductDetails productDetails : productDetailsToOrder){
			int amountToOrder = (productDetails.getMinimumQuantity() - productDetails.getQuantityInStorage() - productDetails.getQuantityInShelves()) + 10;
			Pair<Provider, CatalogItem> minProviderPair = ProviderController.getBestProviderForProduct(productDetails, amountToOrder);
			if(minProviderPair.getFirst() == null){
				//TODO there is no provider which provides the item. maybe print something
			}
			else{
				if(!orders.containsKey(minProviderPair.getFirst())){
					SingleProviderOrder orderToAdd = new SingleProviderOrder(minProviderPair.getFirst(), UUID.randomUUID().toString(), LocalDate.now());
					createWithProviderObj(orderToAdd, minProviderPair.getFirst());
					orders.put(minProviderPair.getFirst(), orderToAdd);
				}
				orders.get(minProviderPair.getFirst()).addToItemList(minProviderPair.getSecond(), amountToOrder);
			}
		}
	}
	public static List<SingleProviderOrder> getAllOrdersOfProvider(String providerId){
		Provider provider = ProviderController.getProvierByID(providerId);
		if(provider == null)
			throw new IllegalArgumentException("there is no provider with id " + providerId);
		return OrdersDAL.getOrdersOfProvider(providerId);
	}
	public static List<SingleProviderOrder> getAllOrders(){
		return OrdersDAL.loadAll();
	}
	public static List<SingleProviderOrder> getAllAutomaticsOrders(){
		return OrdersDAL.getAutomaticOrders();
	}
}
