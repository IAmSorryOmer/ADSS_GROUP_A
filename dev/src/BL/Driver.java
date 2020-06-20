package BL;

import DAL.DDriver;

public class Driver{

//t
    private License license;
    private String name;
    private int id;

    public Driver(License license, String name,int id) {
        this.license = license;
        this.name = name;
        this.id = id;
    }

    public Driver(DDriver dDriver) {
        license = Validation.checkLicense(dDriver.getLicense());
        name = dDriver.getName();
        id = dDriver.getId();
    }

    public License getLicense() {
        return license;
    }


    public String getName() {
        return name;
    }




}//Final version