package BusinessLayer;

public class CatalogItem {
	
	//fields
	private int PriceAfterDiscount=0; //not nedeed
	private int Discount; //discount percentage to apply, example: 5%
	private String CatalogNum;
	private double Price; //regular item price, example: 19.90
	private int OrderQuantity=0; //not nedeed
	
	//consructors
	private CatalogItem (int discount, String catalogNum, double price) {
		this.Discount = discount;
		this.CatalogNum = catalogNum;
		this.Price = price;	
	}
	
	//creators
	public static CatalogItem CatalogItemCreator (int discount, String catalogNum, double price) {
		CatalogItem ci = new CatalogItem(discount, catalogNum, price);
		return ci;
	}

	//methods
	
	
	//getters  & setters
	public int getDiscount() {
		return Discount;
	}

	public void setDiscount(int discount) {
		Discount = discount;
	}

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

public static String printItem(CatalogItem c) {
		String s = "Catalog-Number: " + c.CatalogNum+"\n";
		s+= "Price: "+c.Price+"\n";
		s+= "Discount: "+c.Discount+"\n";
		s+= "Price-After-Discount: "+c.PriceAfterDiscount;
		return s;
	}
	
	
}