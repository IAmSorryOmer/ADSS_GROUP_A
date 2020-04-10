import java.util.HashMap;

public class NumberedFile {
    private String number;
    private String address;
    private HashMap<String,Integer> products;

    public NumberedFile(String number, String address, HashMap<String, Integer> products) {
        this.number = number;
        this.address = address;
        this.products = products;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Integer> products) {
        this.products = products;
    }
}