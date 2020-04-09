public class NumberedFile {
    private String number;
    private String address;
    private String[] productNames;
    private int[] productQuantity;

    public NumberedFile(String number, String address, String[] productNames, int[] productQuantity) {
        this.number = number;
        this.address = address;
        this.productNames = productNames;
        this.productQuantity = productQuantity;
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

    public String[] getProductNames() {
        return productNames;
    }

    public void setProductNames(String[] productNames) {
        this.productNames = productNames;
    }

    public int[] getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int[] productQuantity) {
        this.productQuantity = productQuantity;
    }
}
