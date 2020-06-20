package com.company.Entities;


public class CatalogItem {
	
	//fields
	private String ProviderID;
	private String CatalogNum;
	private double Price; //regular item price, example: 19.90
	private ProductDetails productDetails;

	public CatalogItem(String providerID, String catalogNum, double price, ProductDetails productDetails) {
		ProviderID = providerID;
		CatalogNum = catalogNum;
		Price = price;
		this.productDetails = productDetails;
	}

	public String getProviderID() {
		return ProviderID;
	}

	public void setProviderID(String providerID) {
		ProviderID = providerID;
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
		return productDetails.getName();
	}

	public void setProductDetails(ProductDetails productDetails) {
		this.productDetails = productDetails;
	}

	public ProductDetails getProductDetails() {
		return productDetails;
	}

	public String toString() {
		String s = "Catalog-Number: " + CatalogNum + ", ";
		s += "Item name:" + GetDescribedProductName() + ", ";
		s += "Regular price: " + Price + ".\n";
		return s;
	}
}