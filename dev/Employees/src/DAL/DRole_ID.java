package DAL;

import java.util.List;

public class DRole_ID {

    private  String role;
    private List<Integer> id;

    public DRole_ID(String role, List<Integer> id) {
        this.role = role;
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void addId(int id) {
        this.id.add(id);
    }

    public List<Integer> getId() {
        return id;
    }
}
