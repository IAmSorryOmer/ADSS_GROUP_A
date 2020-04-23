package com.company.LogicLayer;

import java.util.List;

public class ProductDetails implements Discountable, Reportable{
    private String id;
    private String name;
    private String manufacturer;
    private double retailPrice;
    private double supplierPrice;
    private Category category;
    private int quantityInStorage;
    private int quantityInShelves;
    private int minimumQuantity;

    public ProductDetails(String id, String name, String manufacturer, double retailPrice, double supplierPrice, Category category, int minimumQuantity) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.retailPrice = retailPrice;
        this.supplierPrice = supplierPrice;
        this.category = category;
        this.quantityInStorage = 0;
        this.quantityInShelves = 0;
        this.minimumQuantity = minimumQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public double getSupplierPrice() {
        return supplierPrice;
    }

    public void setSupplierPrice(double supplierPrice) {
        this.supplierPrice = supplierPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantityInStorage() {
        return quantityInStorage;
    }

    public void setQuantityInStorage(int quantityInStorage) {
        this.quantityInStorage = quantityInStorage;
    }

    public int getQuantityInShelves() {
        return quantityInShelves;
    }

    public void setQuantityInShelves(int quantityInShelves) {
        this.quantityInShelves = quantityInShelves;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public Discountable getParent() {
        return category;
    }

    @Override
    public String toString() {
        return "Product type: " + name + ", Product type id: " + id + ", Product manufacturer: " + manufacturer +
                "\nStorage Quantity: "  + quantityInStorage + ", Shelves Quantity: " + quantityInShelves;
    }
}
