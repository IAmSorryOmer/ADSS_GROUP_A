package BL;

import DAL.DNumber_Adress;
import DAL.DNumberedFile;
import DAL.DProduct;
import DAL.Mapper;

import java.util.HashMap;
import java.util.List;

public class NumberedFile {
    private String number;
    private String address;
    private HashMap<String,Integer> productNames;

    public NumberedFile(String number, String address, HashMap<String, Integer> productNames) {
        this.number = number;
        this.address = address;
        this.productNames = productNames;
    }

    public NumberedFile(DNumberedFile dNumberedFile) {
        number = dNumberedFile.getNumber();
        address = dNumberedFile.getAddress();
        productNames = new HashMap<>();
    }
    public void load(int deliverId, Mapper mapper){
        List<DProduct> dProducts = mapper.loadProducts(Integer.parseInt(number));
        for (DProduct dProduct: dProducts)
        {
            productNames.put(dProduct.getName(),dProduct.getQuantity());
        }

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