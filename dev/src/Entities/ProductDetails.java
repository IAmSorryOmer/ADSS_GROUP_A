package Entities;

public class ProductDetails implements Discountable, Reportable {
    private String id;
    private String name;
    private String manufacturer;
    private double retailPrice;
    private int daysToExpiration;
    private Category category;
    private int minimumQuantity;


    public ProductDetails(String id, String name, String manufacturer, double retailPrice, int daysOfExpiration, Category category, int minimumQuantity) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.retailPrice = retailPrice;
        this.daysToExpiration = daysOfExpiration;
        this.category = category;
        this.minimumQuantity = minimumQuantity;
    }

    @Override
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

    public int getDaysToExpiration() {
        return daysToExpiration;
    }

    public void setDaysToExpiration(int daysToExpiration) {
        this.daysToExpiration = daysToExpiration;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    @Override
    public Discountable getParent() {
        return category;
    }

    @Override
    public String toString() {
        return "Product type: " + name + ", Product type id: " + id + ", Product manufacturer: " + manufacturer;
    }
}
