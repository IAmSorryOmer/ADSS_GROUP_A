package Tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.*;

import BusinessLayer.Product;

public class Tests {

	
	
	
	
	@BeforeClass
	public static void setup() {
		Product p1 = Product.ProductCreator("tomato");
	}
	
	@Test 
	public static void test1() {
		Assert.assertTrue("test 1", true);
	}
	
	@Test 
	public static void test2() {
		Assert.assertTrue("test 1", true);
	}
}
