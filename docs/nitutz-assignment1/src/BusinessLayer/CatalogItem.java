package BusinessLayer;

public class CatalogItem {
	
	//fields
	//private int PriceAfterDiscount;
	private int Discount; //discount percentage to apply, example: 5%
	private String CatalogNum;
	private double Price; //regular item price, example: 19.90
	//private int OrderQuantity;
	
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
	
	
	
}