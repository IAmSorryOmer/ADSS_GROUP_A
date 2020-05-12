package BL;

import DAL.DNumber_Adress;
import DAL.DNumberedFile;
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

    }
    public void load(int deliverId){
        List<DNumber_Adress> dNumber_adresses = Mapper.loadNumberAdress(deliverId,number);
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