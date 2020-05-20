package DAL;

import java.util.List;

public class DRole_Needed {
    private  String role;
    private int numOfNeeded;

    public DRole_Needed(String role, int numOfNeeded) {
        this.role = role;
        this.numOfNeeded = numOfNeeded;
    }

    public String getRole() {
        return role;
    }



    public int getNumOfNeeded() {
        return numOfNeeded;
    }
}
