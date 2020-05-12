package BL;

import DAL.DDriver;

public class Driver{


    private License license;
    private String name;

    public Driver(License license, String name) {
        this.license = license;
        this.name = name;
    }

    public Driver(DDriver dDriver) {
    }

    public License getLicense() {
        return license;
    }


    public String getName() {
        return name;
    }




}//Final version