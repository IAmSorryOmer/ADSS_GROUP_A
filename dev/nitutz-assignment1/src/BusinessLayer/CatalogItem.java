package BusinessLayer;


public class CatalogItem {
	
	//fields
	//private int Discount; //discount percentage to apply, example: 5%
	private String CatalogNum;
	private Product describedProduct;
	private double Price; //regular item price, example: 19.90
	
	//consructors
	private CatalogItem (String catalogNum, Product describedProduct, double price) {
		this.CatalogNum = catalogNum;
		this.describedProduct = describedProduct;
		this.Price = price;	
	}

	//creators
	public static CatalogItem CatalogItemCreator (String catalogNum, Product describedProduct, double price) {
		CatalogItem ci = new CatalogItem(catalogNum, describedProduct, price);
		return ci;
	}
	
	//getters  & setters
	public String getCatalogNum() {
		return CatalogNum;
	}

	public void setCatalogNum(String catalogNum) {
		CatalogNum = catalogNum;
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
	
 	public static String printItem(CatalogItem c) {
		String s = "Catalog-Number: " + c.CatalogNum + ", ";
		s += "Item name:" + c.GetDescribedProductName() + ", ";
		s += "Regular price: " + c.Price;
		return s;
	}

	
	
}