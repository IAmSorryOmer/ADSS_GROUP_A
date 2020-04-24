package BusinessLayer;

import sun.security.jca.GetInstance;

public class CatalogItem {
	
	//fields
	private int PriceAfterDiscount=0; //not nedeed
	private int Discount; //discount percentage to apply, example: 5%
	private String CatalogNum;
	private Product describedProduct;
	private double Price; //regular item price, example: 19.90
	private int OrderQuantity=0; //not nedeed
	
	//consructors
	private CatalogItem (int discount, String catalogNum, Product describedProduct, double price) {
		this.Discount = discount;
		this.CatalogNum = catalogNum;
		this.describedProduct = describedProduct;
		this.Price = price;	
	}
	
	private CatalogItem (String catalogNum, Product describedProduct, double price) {
		this.Discount = 0;
		this.CatalogNum = catalogNum;
		this.Price = price;	
	}
	
	//creators
	public static CatalogItem CatalogItemCreator (int discount, String catalogNum, Product describedProduct, double price) {
		CatalogItem ci = new CatalogItem(discount, catalogNum, describedProduct, price);
		return ci;
	}

	public static CatalogItem CatalogItemNoDiscountCreator (String catalogNum, Product describedProduct, double price) {
		CatalogItem ci = new CatalogItem(catalogNum, describedProduct, price);
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

	public String GetDescribedProductName() {
		return describedProduct.getName();
	}
	public double calculateFinalPrice(int quantity, int totalOrder, int quantityForDiscount) {
		if(quantityForDiscount > 0) {
		int amountOfDiscounts = totalOrder % quantityForDiscount;
		double finalPrice = quantity * Price * Math.pow(1-(Discount/100),(amountOfDiscounts));
		return finalPrice;
		}
		return quantity*Price;
	}


 	public static String printItem(CatalogItem c) {
		String s = "Catalog-Number: " + c.CatalogNum + "\n";
		s += "Item name:" + c.GetDescribedProductName() + "\n";
		s += "Price: " + c.Price + "\n";
		s += "Discount: " + c.Discount + "\n";
		s += "Price-After-Discount: " + c.PriceAfterDiscount;
		return s;
	}
	
	
}