package DAL;

public class DDestination {
    private String address;
    private String contact;
    private String phone;

    public DDestination(String address, String contact, String phone, String area) {
        this.address = address;
        this.contact = contact;
        this.phone = phone;
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    private String area;
}