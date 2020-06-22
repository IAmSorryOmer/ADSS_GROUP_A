package Entities;

import java.time.LocalDate;

public class Product implements Reportable {

    private String id;
    private int storeId;
    private String location;
    private boolean isInStorage;
    private LocalDate expirationDate;
    private boolean isDamaged;
    private ProductDetails type;


    public Product(String id, int storeId, String location, boolean isInStorage, boolean isDamaged, ProductDetails type) {
        this.id = id;
        this.storeId = storeId;
        this.location = location;
        this.isInStorage = isInStorage;
        this.expirationDate = null;
        this.isDamaged = isDamaged;
        this.type = type;
    }

    public Product(String id, int storeId, String location, boolean isInStorage, LocalDate expirationDate, boolean isDamaged, ProductDetails type) {
        this.id = id;
        this.storeId = storeId;
        this.location = location;
        this.isInStorage = isInStorage;
        this.expirationDate = expirationDate;
        this.isDamaged = isDamaged;
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isInStorage() {
        return isInStorage;
    }

    public void setInStorage(boolean inStorage) {
        isInStorage = inStorage;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public ProductDetails getType() {
        return type;
    }

    public void setType(ProductDetails type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Product id: " + id + ", Product location: " + location + ", name: " + type.getName();
    }
}