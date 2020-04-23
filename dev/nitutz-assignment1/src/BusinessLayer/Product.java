package BusinessLayer;

public class Product {
	
	//fields
	//private String ProductID;
	private String Name;
	
	//consructors
	private Product (String name) {
		//this.ProductID = name;
		this.Name = name;
	}
	
	//creators
	public static Product ProductCreator (String productName) {
		Product product = new Product (productName);
		return product;
	}
	
	//methods
	
	//getters  & setters
	public String getName() {
		return Name;
	}
}
