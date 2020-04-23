import java.util.HashMap;

public class NumberedFile {
    private String number;
    private String address;
    private HashMap<String,Integer> productNames;

    public NumberedFile(String number, String address, HashMap<String, Integer> productNames) {
        this.number = number;
        this.address = address;
        this.productNames = productNames;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }


    public HashMap<String, Integer> getProductNames() {
        return productNames;
    }


}

//Final version