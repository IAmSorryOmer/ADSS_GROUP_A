public class Driver{


    private License license;
    private String name;

    public Driver(License license, String name) {
        this.license = license;
        this.name = name;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}