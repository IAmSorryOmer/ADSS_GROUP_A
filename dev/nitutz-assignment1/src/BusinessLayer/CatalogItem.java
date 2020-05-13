package BusinessLayer;


public class CatalogItem {
	
	//fields
	private final String ProviderID;
	private final String CatalogNum;
	private Product describedProduct;
	private double Price; //regular item price, example: 19.90
	
	
	//consructors
	public CatalogItem (String ProviderID, String catalogNum, Product describedProduct, double price) {
		this.ProviderID = ProviderID;
		this.CatalogNum = catalogNum;
		this.describedProduct = describedProduct;
		this.Price = price;	
	}
	
	
	//getters  & setters
	public String getProviderID() {
		return this.ProviderID;
	}
	
	public String getCatalogNum() {
		return CatalogNum;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}

	public String GetDescribedProductName() {
		return describedProduct.getName();
	}
	
}