package DAL;

import BL.License;

public class DDriver {
    private String license;
    private String name;
    private int id;

    public DDriver(String license, String name,int id){
        this.license = license;
        this.name = name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}