package Tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import static junit.framework.TestCase.*;
import org.junit.*;

import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;

import BusinessLayer.*;

public class Tests {
	public static Provider prov1, prov2;
	public static Product p1, p2, p3;
	
	
	
	@BeforeClass
	public static void setup() {
		p1 = Product.ProductCreator("tomato");
		//create providers
		CommunicationDetails c1 = CommunicationDetails.communicationDetailsCreatorWithAgreement(false, "99999999", "mars", 1000000000);
		prov1 = Provider.ProviderCreator("123456789", "0110", false, 0, new LinkedList<String>(), "bob", c1);
		CommunicationDetails c2 = CommunicationDetails.communicationDetailsCreatorWithAgreement(false, "1", "1241 East Main Street, Stamford, CT 06902", 10);
		prov2 = Provider.ProviderCreator("987654321", "1001", true, 1, new LinkedList<String>(), "john cena", c2);
		AllProviders.getInstance().addProvider(prov1);
		AllProviders.getInstance().addProvider(prov2);
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
	public void test4_() {
		Assert.assertTrue("test 4 failed", true);
	}
	@Test 
	public void test5_() {
		Assert.assertTrue("test 5 failed", true);
	}

}
