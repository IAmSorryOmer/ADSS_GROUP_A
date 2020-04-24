package Tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import static junit.framework.TestCase.*;
import org.junit.*;

import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;
import com.sun.org.apache.xpath.internal.axes.WalkingIteratorSorted;

import BusinessLayer.*;

public class Tests {
	public static Provider prov1, prov2;
	public static Product p1, p2, p3;
	public static SingleProviderOrder spo;
	public static CatalogItem catItem1;
	
	
	
	@BeforeClass
	public static void setup() {
		p1 = Product.ProductCreator("tomato");
		p2 = Product.ProductCreator("potato");
		//create providers
		CommunicationDetails c1 = CommunicationDetails.communicationDetailsCreatorWithAgreement(false, "99999999", "mars", 1000000000);
		prov1 = Provider.ProviderCreator("123456789", "0110", false, 0, new LinkedList<String>(), "bob", c1);
		CommunicationDetails c2 = CommunicationDetails.communicationDetailsCreatorWithAgreement(false, "1", "1241 East Main Street, Stamford, CT 06902", 10);
		prov2 = Provider.ProviderCreator("987654321", "1001", true, 1, new LinkedList<String>(), "john cena", c2);
		spo = SingleProviderOrder.SingleProviderOrderCreator(prov2);
		catItem1 = CatalogItem.CatalogItemCreator(7, "444", p2, 50.00);
	}
	
	@Test 
	public void test1_addItemToCommDetails() {
		CatalogItem cItem1 = CatalogItem.CatalogItemCreator(10, "4654", p1, 9.99);
		Assert.assertTrue("test 1 failed", CommunicationDetails.addCatalogItem(prov2.getCommunicationDetails(), cItem1));
	}
	
	@Test 
	public void test2_FindingNonexistantProvider() {
		Assert.assertTrue("test 2 failed", AllProviders.getInstance().getProvidersByID("lies") == null);
	}
	
	@Test 
	public void test3_FindProvider() {
		Assert.assertTrue("test 3 failed", AllProviders.getInstance().getProvidersByID("987654321").getProviderID().equals(prov2.getProviderID()));
	}
	
	@Test 
	public void test4_CheckOrderSaved() {
		Assert.assertTrue("test 4 failed", !AllOrders.getInstance().getOrders().isEmpty());
	}
	
	@Test 
	public void test5_CheckOrderRelatedToCorrectProvider() {
		Assert.assertTrue("test 5 failed", AllOrders.getInstance().getOrdersFromProvider(prov2).getOrderID().equals(prov2.getProviderID()));
	}
	
	@Test 
	public void test6_CheckTotalItemsCorrect() {
		SingleProviderOrder.AddToOrder(spo, CatalogItem.CatalogItemCreator(10, "12049", p1, 100.00),2);
		SingleProviderOrder.AddToOrder(spo, catItem1 ,3);
		Assert.assertTrue("test 6 failed", AllOrders.getInstance().getOrdersFromProvider(prov2).getTotalItemAmount()==5);
	}
	
	@Test 
	public void test7_CheckRemoveItem() {
		CatalogItem catItem2 = CatalogItem.CatalogItemCreator(1, "111", p2, 10.00);
		SingleProviderOrder.AddToOrder(spo, catItem2, 1);
		Assert.assertTrue("test 7 failed", SingleProviderOrder.RemoveFromOrder(spo, catItem2));
	}
	
	@Test 
	public void test8_CheckRemoveItemFail() {
		CatalogItem catItem3 = CatalogItem.CatalogItemCreator(5, "1241", p2, 110.00);
		Assert.assertTrue("test 8 failed", !SingleProviderOrder.RemoveFromOrder(spo, catItem3));
	}
	
	@Test 
	public void test9_CheckProviderEmptyOrders() {
		Assert.assertTrue("test 9 failed", AllOrders.getInstance().getOrdersFromProvider(prov1).getTotalItemAmount()==0);
	}
	
	@Test 
	public void test10_CheckProviderEditDetails() {
		Provider.editDetails(prov1, prov1.isDoesNeedTransport(), prov1.getDelayDays(), prov1.getArrivalDays(), "steve");
		Assert.assertTrue("test 10 failed", AllProviders.getInstance().getProvidersByID("123456789").getName().equals("steve"));
	}
}
