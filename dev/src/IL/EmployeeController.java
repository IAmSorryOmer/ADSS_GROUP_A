package IL;

import BL.Employee;
import BL.Store;

import java.util.List;

public class EmployeeController {
    final int storeNumbers = 9;
    public Employee activeUserEmployee;
    private List<Store> stores;


    private static EmployeeController instance = null;

    public static EmployeeController getInstance()
    {
        if (instance == null)
        {
            instance = new EmployeeController();
        }
        return  instance;
    }

    public void  setActiveUserEmployee(Employee employee)
    {
        this.activeUserEmployee = employee;
    }

    public boolean hasActiveUserAssignedShifts()
    {
        return activeUserEmployee.getHasAssignedShifts();
    }

    public String getActiveName()
    {
        return activeUserEmployee.getName();
    }
    public boolean connect(int id)
    {
        for (Store store :stores)
        {
            for (Employee employee: store.getEmployees())
            {
                if (employee.getID() == id) {
                    activeUserEmployee = employee;
                    return true;
                }
            }
        }
        return false;
    }
    public String watchMyShifts()
    {
        Store store = getStore(activeUserEmployee.getStore_num());
        String shifts = "";
        for (int i = 0; i < 7; i++)
        {
            for (String role : store.getSchedule().getDays()[i].getMorning().keySet())
            {
                for (int j=0; j <store.getSchedule().getDays()[i].getMorning().get(role).length; j++)
                {
                    if (store.getSchedule().getDays()[i].getMorning().get(role)[j] == activeUserEmployee.getID())
                       shifts += "in day " + (i+1) + " morning shift as: " + role +"\n";
                }
            }

            for (String role : store.getSchedule().getDays()[i].getEvening().keySet())
            {
                for (int j=0; j <store.getSchedule().getDays()[i].getEvening().get(role).length; j++)
                {
                    if (store.getSchedule().getDays()[i].getEvening().get(role)[j] == activeUserEmployee.getID())
                        shifts += "in day " + (i+1) + " evening shift as: " + role +"\n";
                }
            }

        }
        if (shifts.equals(""))
            shifts = "no shifts at the moment";
        return shifts;
    }
    public String enterMyCapableShifts(int [] shifts_Num)
    {
        if (activeUserEmployee.getHasAssignedShifts())
        {
            return "you already assigned shifts for this week";
        }
        String result = Validation.checkSelectedShifts(shifts_Num);
        if (result.equals("success")) {
            String[] myShifts = new String[7];

            for (int i = 0; i < shifts_Num.length; i++) {
                if (shifts_Num[i] % 2 == 0) {
                    myShifts[(shifts_Num[i] - 1) / 2] += "evening";
                } else {
                    myShifts[shifts_Num[i] / 2] += "morning";
                }
            }

            activeUserEmployee.setCapable_shifts(myShifts);
        }
        return result;
    }

    public Store getStore(int store_num)
    {
        for (int i = 0; i < storeNumbers; i++) {

            if (stores.get(i).getStore_num() == store_num) {
                return stores.get(i);
            }
        }
        return null;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}
