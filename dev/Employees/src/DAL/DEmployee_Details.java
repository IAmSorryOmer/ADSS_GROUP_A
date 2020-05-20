package DAL;

public class DEmployee_Details {
    private int id;
    private int store_num;
    private String name;

    public int getId() {
        return id;
    }

    public int getStore_num() {
        return store_num;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DEmployee_Details(int id, int store_num, String name) {
        this.id = id;
        this.store_num = store_num;
        this.name = name;
    }
}
