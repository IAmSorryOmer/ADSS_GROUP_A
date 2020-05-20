package BL;

import DAL.DDestination;
import DAL.DNumberedFile;

public class Destination {

    private String address;
    private String contact;
    private String phone;
    private String area;

    public Destination(String address, String contact, String phone, String area) {
        this.address = address;
        this.contact = contact;
        this.phone = phone;
        this.area = area;
    }

    public Destination(DDestination dDestination) {
        this.address = dDestination.getAddress();
        this.contact = dDestination.getContact();
        this.phone = dDestination.getPhone();
        this.area = dDestination.getArea();
    }


    public String getAddress() {
        return address;
    }


    public String getContact() {
        return contact;
    }


    public String getPhone() {
        return phone;
    }


    public String getArea() {
        return area;
    }

}


//Final version